package top.strelitzia.models;

public class LoginInfo {
    
    private Boolean ok;
    
    private String token;
    
    private UserInfo userInfo;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
