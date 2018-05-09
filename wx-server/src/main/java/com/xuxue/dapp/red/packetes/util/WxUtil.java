package com.xuxue.dapp.red.packetes.util;


import com.jayway.jsonpath.JsonPath;
import com.xuxue.dapp.red.packetes.Configure;
import com.xuxue.dapp.red.packetes.HttpsRequest;
import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.model.FailedEntry;
import com.xuxue.dapp.red.packetes.model.ObjectEntry;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WxUtil {
    private static final Logger LOG = LoggerFactory.getLogger(WxUtil.class);


    /**
     * 获取微信连接票据，每1小时50分钟一次
     *
     */
    public static void getAccessToken(){
        try{
            List<HashMap> params = new ArrayList<>();
            HashMap<String,String> grant_type = new HashMap<>();
            grant_type.put("key", "grant_type");
            grant_type.put("val", "client_credential");
            params.add(grant_type);
            HashMap<String,String> appid = new HashMap<>();
            appid.put("key", "appid");
            appid.put("val", Configure.getAppid());
            params.add(appid);
            HashMap<String,String> secret = new HashMap<>();
            secret.put("key", "secret");
            secret.put("val", Configure.getAppSecret());
            params.add(secret);
            HttpsRequest hr = new HttpsRequest();
            String accessToken = hr.sendPost(Configure.ACCESS_TOKEN_API, params);
            LOG.info("Request access_token response {}",accessToken);
            JSONObject ato = JSONObject.fromObject(accessToken);
            Configure.access_token = ato.get("access_token").toString();
        }catch (Exception ex){
            LOG.warn("Failed request access_token!",ex);
        }
    }

    public static void getJsapiTicket(){
        if(Configure.access_token == null || Configure.access_token.isEmpty()){
            WxUtil.getAccessToken();
        }

        HttpUriRequest request = RequestBuilder.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket")
                .addParameter("access_token",Configure.access_token)
                .addParameter("type","jsapi")
                .build();
        try(CloseableHttpClient client = HttpClients.createDefault()){
            try(CloseableHttpResponse response = client.execute(request)){
                String result = EntityUtils.toString(response.getEntity());
                String ticket = JsonPath.read(result,"$.ticket");
                Configure.jsapi_ticket = ticket;
            }
        }catch (Exception ex){
            LOG.warn("Failed request!",ex);
        }
    }

    public static Entry getOpenIdByCode(String code){
        LOG.info("Request user info! code {}",code);
        HttpUriRequest request = RequestBuilder.get("https://api.weixin.qq.com/sns/oauth2/access_token")
                .addParameter("appid",Configure.getAppid())
                .addParameter("secret",Configure.getAppSecret())
                .addParameter("code",code)
                .addParameter("grant_type","authorization_code")
                .build();
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HashMap<String,Object> data;
            try(CloseableHttpResponse response = client.execute(request)){
                String result = EntityUtils.toString(response.getEntity());
                LOG.info(result);
                data =  G.gson.fromJson(result,HashMap.class);
            }
            HttpUriRequest userInfo = RequestBuilder.get("https://api.weixin.qq.com/sns/userinfo")
                    .addParameter("access_token",data.get("access_token").toString())
                    .addParameter("openid",data.get("openid").toString())
                    .addParameter("lang","zh_CN")
                    .build();
            try(CloseableHttpResponse response = client.execute(userInfo)){
                String result = EntityUtils.toString(response.getEntity(),"utf-8");
                LOG.info(result);
                data =  G.gson.fromJson(result,HashMap.class);
            }
            return new ObjectEntry(data);
        }catch (Exception ex){
            LOG.warn("",ex);
            return new FailedEntry(ex);
        }
    }





}
