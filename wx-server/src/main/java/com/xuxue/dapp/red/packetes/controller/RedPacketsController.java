package com.xuxue.dapp.red.packetes.controller;

import com.xuxue.dapp.red.packetes.model.Entry;
import com.xuxue.dapp.red.packetes.service.RedPacketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("wx/reds/red")
@RestController
public class RedPacketsController {

    @Autowired
    private RedPacketsService redPacketsService;

    @PostMapping(value="take")
    public Entry take(String address,String redAddress,String redId,String openid){
        return redPacketsService.take(address,redAddress,redId,openid);
    }


    @GetMapping(value="message")
    public Entry message(String id){
        return redPacketsService.message(id);
    }

}
