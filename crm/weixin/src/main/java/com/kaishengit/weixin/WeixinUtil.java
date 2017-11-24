package com.kaishengit.weixin;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeiXinException;
import okhttp3.*;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class WeixinUtil {

    //  通讯录的secret
    private String contactsSecret = "uo4T-XoA7KdcCmyIX3UXELYSjBggYB5rMEM36jHjJ2w";
    //crm的secret
    private String crmSecret = "uUgeo-JrqPUG8cNl7gddTCwrHYKmxuP37pH3wbmhybU";
    //crm的agentId
    private String crmAgentId = "1000002";
    //企业ID
    private String cropId = "wwa2a52eb94595a02d";
    //获取token的url
    private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    //发送消息的url
    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
    //创建部门的url
    private static final String CREATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";


    //获取token时做的判断
    public static final String ACCESSTOKEN_TYPE_NORMAL = "normal";
    public static final String ACCESSTOKEN_TYPE_CONTACTS = "contacts";


    //获取并缓存token
    private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    //判断获取的是普通的AccessToken还是通讯录的AccessToken
                    if (ACCESSTOKEN_TYPE_CONTACTS.equals(type)) {
                        url = String.format(GET_TOKEN_URL, cropId, contactsSecret);
                    } else {
                        url = String.format(GET_TOKEN_URL, cropId, crmSecret);
                    }
                    String resultJson = sendHttpGetRequest(url);
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if (map.get("errcode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw new WeiXinException(resultJson);
                }
            });
    //发起get请求
    String sendHttpGetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    //发起post请求
    String sendHttpPostRequest(String url, String json) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //获取access_token
    public String getToken(String type){
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            throw new WeiXinException("获取token异常");
        }
    }

    //发送消息
    @Test
    public void sendMessage() throws IOException {
        //将url中的字符进行替换
       String url = String.format(SEND_MESSAGE_URL,getToken(ACCESSTOKEN_TYPE_NORMAL));
        System.out.println(url);
       Map<String,Object> messageMap = new HashMap<String,Object>();
       messageMap.put("toparty",1);
       messageMap.put("msgtype","text");//ok
       messageMap.put("agentid",crmAgentId);//ok
       Map<String, String> contextMap = new HashMap<String, String>();
       contextMap.put("content","你的快递到了");//ok
       messageMap.put("text", contextMap);//ok
       //将map集合转换成json格式的string
       String stirngMessage = JSON.toJSONString(messageMap);
        System.out.println(stirngMessage);
       String resultJson = sendHttpPostRequest(url,stirngMessage);
       Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
       if(!resultMap.get("errmsg").equals("ok")){
           System.out.println(resultMap.get("errcode"));
            throw new WeiXinException("发送消息异常");
       }
    }
    //新建部门
    public void createDept(String deptName,Integer pid,Integer deptId) throws IOException {
        //将url中的字符进行替换  %s -> contacts
        String url = String.format(CREATE_DEPT_URL,getToken(ACCESSTOKEN_TYPE_CONTACTS));

        Map<String,Object> messageMap = new HashMap<String,Object>();
//        添加的部门名称
        messageMap.put("name",deptName);
        messageMap.put("id",deptId);
//        pid
        messageMap.put("parentid",pid);
        String stringMessage = JSON.toJSONString(messageMap);
        String resultJson = sendHttpPostRequest(url,stringMessage);
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeiXinException("创建部门失败:" + resultJson);
        }
    }
    //删除部门
    //

}

