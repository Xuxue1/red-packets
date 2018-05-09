package com.xuxue.dapp.red.packetes.service;

import com.xuxue.dapp.red.packetes.util.WxUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefrashApiTicket {

    @Scheduled(fixedDelay = 1000*6000)
    public void print(){
        WxUtil.getJsapiTicket();
    }

}
