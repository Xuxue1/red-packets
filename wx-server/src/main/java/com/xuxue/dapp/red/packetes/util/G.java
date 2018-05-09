package com.xuxue.dapp.red.packetes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class G {
    public  static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
}
