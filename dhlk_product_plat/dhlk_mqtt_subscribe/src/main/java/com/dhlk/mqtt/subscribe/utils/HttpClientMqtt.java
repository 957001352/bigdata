package com.dhlk.mqtt.subscribe.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/7
 */
public class HttpClientMqtt {

    //config
    public static final String HOST = "tcp://192.168.2.161:1883";
    public static final String TOPIC = "machine";
    private static final String clientid = "dhlk_plat_client";
    private Map<String,String> apiList=null;
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "password";

    public void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            //options.setUserName(userName);
            // 设置连接的密码
           // options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            //读取转发请求接口
            apiList=this.readApiList();
            // 设置回调
            client.setCallback(new MqttCallback(){
                @SneakyThrows
                public void connectionLost(Throwable cause) {
                    // 连接丢失后，一般在这里面进行重连
                    String result="{\"code\": 0,\"msg\": \"连接断开，请联系据提供方\",\"data\":\"\"}";
                    for(String key : apiList.keySet()){
                        doPost(httpClient,key,result);
                    }
                    System.out.println("连接断开，请重新连接");
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    try {
                        for(Map.Entry<String, String> entry : apiList.entrySet()){
                            String url = entry.getKey();
                            String top = entry.getValue();
                            JSONObject object=JSONObject.parseObject(message.toString());
                            doPost(httpClient,url,message.toString());
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            MqttTopic topic = client.getTopic(TOPIC);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic, "close".getBytes(), 2, true);

            client.connect(options);
            //订阅消息
            int[] Qos  = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(CloseableHttpClient client, String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(data, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
        CloseableHttpResponse response = client.execute(httpPost);
        if (httpPost != null) {
            httpPost.releaseConnection();
        }
    }
    public  Map<String,String> readApiList() throws IOException {
        File file= ResourceUtils.getFile("classpath:ApiList.txt");
        String s="";
        Map<String,String> res=new HashMap<String,String>();
        InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(in);
        while ((s = br.readLine()) != null) {
            String[] strs=  s.split("topic=");
            if(strs!=null&&strs.length>1){
                String url=strs[0].replace("url=","");
                String topic=strs[1].replace("topic=","");
                res.put(url,topic);
            }
        }
        return res;
    }
    public static void main(String[] args) throws Exception {
       HttpClientMqtt client = new HttpClientMqtt();
       client.start();
    }


}