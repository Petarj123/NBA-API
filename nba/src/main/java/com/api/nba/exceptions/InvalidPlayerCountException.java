package com.api.nba.exceptions;

public class InvalidPlayerCountException extends Exception {

    public InvalidPlayerCountException(String msg){
        super(msg);
    }
}
