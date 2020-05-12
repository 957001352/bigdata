package com.dhlk.systemconst;


import java.util.Map;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/2
 */
public class Const {
    public final static String SOFTWARECOMPANYNAME = "陕西大航联科科技有限公司";
    public final static String LIMITWORDS = "'||*"; //非法字符
    public final static String LIMITWORDS2 = "’||❄"; //替换字符

    public final static String SYTEMEMAIL = "master@dhlk.com";
    public final static String SYSTEMEAILPASSWORD = "master@dhlk.com";
    public final static String SYSTEMESTMP = "stmp@dhlk.com";

    /**
     * 状态
     * gchen
     */
    public final static Integer STATUS_DELETE = 2;
    public final static Integer STATUS_OK = 0;
    public final static Integer STATUS_BAN = 1;

    /**
     *  前端传入token的header的键
     *  gchen
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     *  token缓存前缀
     *  gchen
     */
    public static final String TOKEN_CACHE_PREFIX = "dhlk.cache.token.";

    /**
     *  用户权限缓存前缀
     *  gchen
     */
    public static final String PERMISSIONS_CACHE_PREFIX = "dhlk.cache.permissions.";

    /**
     *  token的过期时间，24个小时
     *  gchen
     */
    public static final long TOKEN_LOSE_TIME = 12*60*60;

    /**
     * 系统允许的文件类型
     */
    public final static String UNLIMITFILENAME = "json|xml|gif|jpg|png|doc|docx|xls|xlsx|ppt|pptx|txt|wps|pdf|bmp|dwg";

    /**
     * 系统允许上传文件的大小(单位：MB)
     */
    public final static Integer ALLOWUPLOADFILESIZE = 200;

    /**
     * 短信类常数,根据需要增加
     */
    public final static String SMSOFFICALURL = "";

    //tb接口api
    public final static String TBLOGINSUCCESSTOKEN="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5d3hmZGo5OTk5QGxlYW5zaXRlLmNuIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJhMTY1NWU2MC0yMGExLTExZWEtOGY2Ni0yNTQzMjcxOTY2OWQiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiYTE0OGQ1YjAtMjBhMS0xMWVhLThmNjYtMjU0MzI3MTk2NjlkIiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNTg0OTQ0NjM5LCJleHAiOjE1ODQ5NTM2Mzl9.JGKd-zE5VatXWIhWtD1EZsOfqGMkm_3wrWJ4LAmUpaFWWEcffV740yp_0H3wT2KT3RvcbDAlVNfGY0iKC45q8A";
    //用户登录
    public final static String TBUSERLOGIN="/api/auth/login";
    //保存设备
    public final static String TBSAVEDEVICE = "/api/device";
    //删除设备  /api/device/{deviceId}
    public final static String TBDELETEDEVICEBYID = "/api/device";
    //根据id查询设备 /api/device/{deviceId}
    public final static String SELECTTBDEVICEBYID = "/api/device";
    //保存设备共享属性 /api/plugins/telemetry/{entityType}/{entityId}/{scope}
    public final static String SAVEDEVICESHAREDATTRIBUTE= "/api/plugins/telemetry";
    //获取设备历史遥测数据 /api/plugins/telemetry/{entityType}/{entityId}/values/timeseries
    public final static String GETTIMESERIES= "/api/plugins/telemetry";
    //警报 /api/alarm/{entityType}/{entityId}{?searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator}
    public final static String GETALARMS= "/api/alarm";
    //根据设备名字查询设备信息 /api/tenant/devices{?deviceName}
    public final static String GETTENANTDEVICE= "/api/tenant/devices";



    //flume认证加密私钥
    public final static String FLUMNAUTHPRIVATEKEY = "dhlk_flume";


    /**
     *收件人
     */
    public static Map<String,String> receiverMap=null;
    /**
     * 发送邮件主题
     */
    public static String subject="";
    /**
     * 发送内容
     */
    public static String content="";
    /**
     * 附件地址
     */
    public static String filePath="";


    /**
     * bi 代理相关信息
     */
    public static final String BI_PROXY_SERVER_IP = "192.168.2.114";
    public static final int BI_PROXY_SERVER_PROT = 8090;
    public static final String BI_PROXY_SERVER_USERNAME = "admin";
    public static final String BI_PROXY_SERVER_PASSWORD = "admin";

    public static String BI_PROXY_SERVER_TOKEN = "";

    public static final String BI_LOCAL_NODERED_URL = "localhost:7070";
    public static final int BI_MIN_PORT = 1001;
    public static final int BI_MAX_PORT = 1999;
    public static final String BI_NODERED_SERVER_NAME = "nodered";
    public static final String BI_NAME_PREFIX = "BI-";

}
