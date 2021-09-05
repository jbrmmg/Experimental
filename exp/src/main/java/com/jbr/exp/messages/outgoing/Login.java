package com.jbr.exp.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.jbr.exp.messages.BaseMessageType;

public class Login extends BaseMessageType {
    private final String encryptType;
    private final String loginType;
    private final String password;
    private final String username;

    public Login(String username, String password) {
        super((short)1000);
        this.encryptType = "MD5";
        this.loginType = "DVRIP-Web";
        this.password = password;
        this.username = username;
    }

    @JsonGetter("EncryptType")
    public String getEncryptType() {
        return encryptType;
    }

    @JsonGetter("LoginType")
    public String getLoginType() {
        return loginType;
    }

    @JsonGetter("PassWord")
    public String getPassWord() {
        return password;
    }

    @JsonGetter("UserName")
    public String getUserName() {
        return username;
    }
}
