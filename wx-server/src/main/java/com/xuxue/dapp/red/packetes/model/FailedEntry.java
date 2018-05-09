package com.xuxue.dapp.red.packetes.model;

public class FailedEntry extends Entry{

    public FailedEntry(Exception ex){
        setSuccess(false);
        setErrorMessage(ex.getMessage());
    }



}
