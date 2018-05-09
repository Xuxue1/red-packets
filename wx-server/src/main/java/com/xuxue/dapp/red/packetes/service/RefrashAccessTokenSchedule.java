package com.xuxue.dapp.red.packetes.service;

import com.xuxue.dapp.red.packetes.Configure;
import com.xuxue.dapp.red.packetes.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefrashAccessTokenSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(RefrashAccessTokenSchedule.class);


    @Scheduled(fixedDelay = 1000*7000)
    public void print(){
        WxUtil.getAccessToken();
        LOG.info("Success request jsApiTicket! {}", Configure.access_token);
    }

}
