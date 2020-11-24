package com.camelrest.excercise.exception;

public class NetworkAPIException extends  Exception{
    public NetworkAPIException(){

    }

    public NetworkAPIException(String exceptionMessage){
        super(exceptionMessage);
    }
}
