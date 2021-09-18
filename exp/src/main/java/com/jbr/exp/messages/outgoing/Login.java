package com.jbr.exp.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.jbr.exp.messages.BaseMessageType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends BaseMessageType {
    private final String encryptType;
    private final String loginType;
    private final String password;
    private final String username;

    @SuppressWarnings("SpellCheckingInspection")
    public Login(String username, String password) {
        super((short)1000);
        this.encryptType = "MD5";
        this.loginType = "DVRIP-Web";
        this.password = encryptPassword(password);
        this.username = username;
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
            byte[] theDigest = md.digest(bytesOfPassword);

            String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder result = new StringBuilder();

            for(int x = 0; x < theDigest.length; x = x + 2) {
                int x1 = theDigest[x] & 0xFF;
                int x2 = 0;

                if(theDigest.length >= x + 1) {
                    x2 = theDigest[x+1]& 0xFF;
                }

                result.append(chars.charAt((x1 + x2) % 62));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Failed to encrypt the password " + e.getMessage());
        }

        // If the encryption didn't work then just return the password provided.
        return password;
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
