package top.strelitzia.models;

import lombok.Data;

@Data
public class LoginInfo {
    
    private Boolean ok;
    
    private String token;
    
    private UserInfo userInfo;
}
