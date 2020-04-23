package com.dhlk.basicmodule.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.tb.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import service.RedisBasicService;
import systemconst.Const;
import utils.HttpClientResult;
import utils.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/* Content: HTTP请求工具类
  * Author:jlv
  * Date:2020/3/22
 */
@Component
public class RestTemplateUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisBasicService redisService;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Value("${tb.login.username}")
    private String username;

    @Value("${tb.login.password}")
    private String password;

    public String getTbJwtToken(){
        //tb默认的token有效期为9000s,即2.5小时
        if(redisService.hasKey("tbJwtToken")){
            //token有效 直接从本地线程变量中获取token
            return redisService.get("tbJwtToken").toString();
        }else {
            //token失效 调用tb登录接口获取token
            return loginSucessToken();
        }
    }

    /* tb登录接口
     */
    public String loginSucessToken(){
        TbUser user=new TbUser(username,password);
        ResponseEntity<Map> responseEntity =restTemplateExchange(Const.TBUSERLOGIN, HttpMethod.POST, user, Map.class,false);
        //在登录tb成功之后，把返回的token存到redis中
        redisService.set("tbJwtToken","Bearer "+responseEntity.getBody().get("token"),3600);
        return  "Bearer "+responseEntity.getBody().get("token");
    }

    /*Get请求
     *@param api 请求api 可在后面直接带参数
     * @param responseType 返回值类型
     * @param requestToken 请求tb接口时 请求接口是否需要tb jwt token header是否需要设置X-Authorization
     */
    public  <T,V> ResponseEntity<T> getRestTemplate(String api,Class<T> responseType,Boolean requestToken){
        return  restTemplateExchange(api,HttpMethod.GET,null,responseType,requestToken);
    }
    /* Post请求
     * @param api 请求api 可在后面直接带参数
     * @param responseType 返回值类型
     * @param v POST(保存或更新实体)
     * @param requestToken 请求tb接口时 请求接口是否需要tb jwt token header是否需要设置X-Authorization
     */
    public  <T,V> ResponseEntity<T> postRestTemplate(String api,V v,Class<T> responseType,Boolean requestToken){
        return  restTemplateExchange(api,HttpMethod.POST,v,responseType,requestToken);
    }
    /* Put请求
    * @param api 请求api 可在后面直接带参数
     * @param responseType 返回值类型
     * @param requestToken 请求tb接口时 请求接口是否需要tb jwt token header是否需要设置X-Authorization
     */
    public  <T,V> ResponseEntity<T> putRestTemplate(String api,Class<T> responseType,Boolean requestToken){
        return  restTemplateExchange(api,HttpMethod.PUT,null,responseType,requestToken);
    }
    /* delete请求
     * @param api 请求api 可在后面直接带参数
     * @param responseType 返回值类型
     * @param requestToken 请求tb接口时 请求接口是否需要tb jwt token header是否需要设置X-Authorization
     */
    public  <T,V> ResponseEntity<T> deleteRestTemplate(String api,Class<T> responseType,Boolean requestToken){
        return  restTemplateExchange(api,HttpMethod.DELETE,null,responseType,requestToken);
    }
    /* @param api 请求api
     * @param httpMethod 请求方式  GET POST PUT DELETE
     * @param v POST(保存或更新实体)
     * @param responseType 返回值类型
     * @param requestToken 请求tb接口时  请求接口是否需要tb jwt token header是否需要设置X-Authorization
     */
    public  <T,V> ResponseEntity<T> restTemplateExchange(String api,HttpMethod httpMethod, V v,Class<T> responseType,Boolean requestToken){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(requestToken){
            headers.add("X-Authorization", getTbJwtToken());
        }
        HttpEntity<V> entity=null;
        if(v==null){
            entity= (HttpEntity<V>)new HttpEntity<String>(headers);
        }else{
            entity = new HttpEntity<V>(v,headers);
        }
        String url=tbBaseUrl+api;
        return  restTemplate.exchange(url,httpMethod,entity,responseType);
    }
    /* tb登录接口
     */
    public String loginSucessToken1() throws Exception {
        TbUser user=new TbUser(username,password);
        //设置请求头
        Map<String, String> headers=getHeaders(false);
        //发送post请求
        HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBUSERLOGIN, headers, JSON.toJSONString(user));
        Map map = JSON.parseObject(httpClientResult.getContent(),Map.class);
        String token="Bearer "+map.get("token").toString();
        //在登录tb成功之后，把返回的token存到redis中
        redisService.set("tbJwtToken",token,3600);
       return token;
    }

    public String getTbJwtToken1() throws Exception {
        //tb默认的token有效期为9000s,即2.5小时
        if(redisService.hasKey("tbJwtToken")){
            //token有效 直接从本地线程变量中获取token
            return redisService.get("tbJwtToken").toString();
        }else {
            //token失效 调用tb登录接口获取token
            return loginSucessToken1();
        }
    }
    /**
     * Description: 封装请求头
     *
     * @param requestToken 请求接口是否需要token
     * @throws IOException
     */
    public Map<String, String> getHeaders(Boolean requestToken) throws Exception {
        Map<String, String> headers=new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        if(requestToken){
            headers.put("X-Authorization", getTbJwtToken1());
        }
        return headers;
    }
}
