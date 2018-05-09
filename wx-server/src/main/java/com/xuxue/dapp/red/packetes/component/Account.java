package com.xuxue.dapp.red.packetes.component;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class Account {
    private static final Logger LOG = LoggerFactory.getLogger(Account.class);

    private String secret;
    private String password;


    @PostConstruct
    public void init()throws Exception{
        try(InputStream inputStream = new FileInputStream("secret")){
            secret = IOUtils.toString(inputStream);
        }catch (Exception ex){
            try(InputStream inputStream = getClass().getResourceAsStream("/secret")){
                secret = IOUtils.toString(inputStream);
            }
        }
        try(InputStream inputStream = new FileInputStream("password")){
            password = IOUtils.toString(inputStream);
        }catch (Exception ex){
            try(InputStream inputStream = getClass().getResourceAsStream("/password")){
                password = IOUtils.toString(inputStream);
            }
        }

        LOG.info("secret {}",secret);
        LOG.info("password {}",password);
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
