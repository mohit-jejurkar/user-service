package com.example.user_service.common;

import java.util.UUID;

public class CreateTransactionId {

    public static String getTransactionId(){
        return UUID.randomUUID().toString();
    }
}
