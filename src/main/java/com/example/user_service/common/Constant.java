package com.example.user_service.common;

public class Constant {
    public interface Request {
        String acknowledgement = "acknowledgement";
        String timestamp = "timestamp";
        String status = "STATUS";
        String success = "SUCCESS";
        String error = "ERROR";
        String path = "path";
    }

    public interface Transaction {
        String inprocess = "IN-PROCESS";
        String completed = "COMPLETED";
        String userExists = "USER_EXITS";
        String failed = "FAILED";
        String error = "ERROR";
        String success = "SUCCESS";


    }


}
