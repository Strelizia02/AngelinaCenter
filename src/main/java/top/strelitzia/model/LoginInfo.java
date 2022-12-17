package top.strelitzia.model;

import lombok.Data;

/**
 * 登录的返回信息封装
 */
@Data
public class LoginInfo {
    
    private Boolean ok;
    
    private String token;
    
    private UserInfo userInfo;
}
