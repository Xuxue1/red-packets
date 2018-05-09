package com.xuxue.dapp.red.packetes.model;

import java.util.List;

public class ArrayEntry extends SuccessEntry{

    private List<? extends Object> data;
    private long total;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setData(List<? extends Object> data) {
        this.data = data;
    }

    public List<? extends Object> getData() {
        return data;
    }
}
