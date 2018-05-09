package com.xuxue.dapp.red.packetes.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class Take {


    public static String take(String accountMessage,
                              String redAddress,
                              String redId,
                              String address,
                              String wxId,
                              String password)throws Exception{
        Map<String,Object> data = new HashMap<>();
        data.put("account",accountMessage);
        data.put("address",address);
        data.put("password",password);
        data.put("red_id",redId);
        data.put("wx_id",wxId);
        data.put("red_address",redAddress);
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost post = new HttpPost("http://localhost:8081/take");
            post.setEntity(new StringEntity(G.gson.toJson(data)));
            try(CloseableHttpResponse response = client.execute(post)){
                return EntityUtils.toString(response.getEntity());
            }
        }
    }
}
