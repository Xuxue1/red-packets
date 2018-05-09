package com.xuxue.dapp.red.packetes.service;


import com.xuxue.dapp.red.packetes.domain.User;
import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.model.ObjectEntry;
import com.xuxue.dapp.red.packetes.repository.UserRepository;
import com.xuxue.dapp.red.packetes.util.G;
import com.xuxue.dapp.red.packetes.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;


@Service
public class WxAuthenticationService {
    private static final Logger LOG = LoggerFactory.getLogger(WxAuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    public Entry getOpenId(String code){
        Entry entry =  WxUtil.getOpenIdByCode(code);
        if(entry instanceof ObjectEntry){
            Map data = (Map)((ObjectEntry)entry).getData();
            String openId = data.get("openid").toString();
            LOG.info(G.gson.toJson(entry));
            User user = userRepository.findUserByOpenid(openId);
            if(user == null){
                user =  new User();
                user.setOpenId(data.get("openid").toString());
                user.setEndTime(new Date());
                user.setLevel(1);
                user.setStartTime(new Date());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR,3);
                user.setEndTime(calendar.getTime());
                user.setCity(data.get("city").toString());
                user.setCountry(data.get("country").toString());
                user.setHeadImgurl(data.get("headimgurl").toString());
                user.setNickName(data.get("nickname").toString());
                user.setProvince(data.get("province").toString());
                user.setSex(data.get("sex").toString());
                userRepository.save(user);
            }
            return new ObjectEntry(data);
        }else{
            return entry;
        }
    }

}
