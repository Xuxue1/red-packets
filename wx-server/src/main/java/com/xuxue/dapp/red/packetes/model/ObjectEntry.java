package com.xuxue.dapp.red.packetes.model;

public class ObjectEntry extends SuccessEntry{

    private Object data;

    public ObjectEntry(){}

    public ObjectEntry(Object data){
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
