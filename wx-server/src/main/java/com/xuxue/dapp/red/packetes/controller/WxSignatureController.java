package com.xuxue.dapp.red.packetes.controller;


import com.xuxue.dapp.red.packetes.Configure;
import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.model.FailedEntry;
import com.xuxue.dapp.red.packetes.model.ObjectEntry;
import com.xuxue.dapp.red.packetes.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("wx/rest")
@RestController
public class WxSignatureController {
    private static final Logger LOG = LoggerFactory.getLogger(WxSignatureController.class);


    @GetMapping(value = "/signature")
    public Entry getSignature(String url){
        if(url.contains("#")){
            url = url.replaceAll("#.*?$","");
        }
        LOG.info("URL {}",url);
        String nonstr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);;
        String timestamp = System.currentTimeMillis()/1000+"";
        if(Configure.jsapi_ticket==null || Configure.jsapi_ticket.isEmpty()){
            WxUtil.getJsapiTicket();
        }
        LOG.info("ticket {}",Configure.jsapi_ticket);
        String jsapi_ticket = Configure.jsapi_ticket;
        String signatureString = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonstr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signatureString.getBytes("UTF-8"));
            String signature = byteToHex(crypt.digest());

            Map<String,Object> data = new HashMap<>();
            data.put("appId",Configure.getAppid());
            data.put("timestamp",timestamp);
            data.put("nonceStr",nonstr);
            data.put("signature",signature);
            return new ObjectEntry(data);
        }catch (Exception ex){
            return new FailedEntry(ex);
        }
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
