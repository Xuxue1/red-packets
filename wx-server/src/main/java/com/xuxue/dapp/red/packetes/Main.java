package com.xuxue.dapp.red.packetes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class Main {


    static {

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

    }

    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Main.class);
        app.run();
    }

}
