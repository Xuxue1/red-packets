package com.xuxue.dapp.red.packetes.controller;


import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.service.WxAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("wx/rest")
@RestController
public class WxAuthentication {

    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    @GetMapping(value = "/authentication")
    public String authentication(String signature,String timestamp,String nonce,String echostr){
        return echostr;
    }

    @GetMapping(value = "/open_id")
    public Entry getOpenId(String code){
        return wxAuthenticationService.getOpenId(code);
    }

}
