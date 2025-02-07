package com.example.catlib_0612.data;

public class UserFeedback {
    private String account;
    private String feedback;

    public UserFeedback(String _account,String _feedback){
        this.account = _account;
        this.feedback = _feedback;
    }

    public String getAccount() {
        return account;
    }

    public String getFeedback() {
        return feedback;
    }
}
