package messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;
import messages.BaseMessageType;

public class Login extends BaseMessageType {
    private String encryptType;
    private String loginType;
    private String password;
    private String username;

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
