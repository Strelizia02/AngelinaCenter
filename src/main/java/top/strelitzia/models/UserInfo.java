package top.strelitzia.models;

import lombok.Data;

/**
 * 登录信息封装
 */
@Data
public class UserInfo {
    /**
     * 登录id
     */
    private String id;
    /**
     * 登录密码
     */
    private String pwd;
    /**
     * 是否是注册用户
     */
    private Integer isRegister;
    /**
     * 是否是Bot用户
     */
    private Integer isBot;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String name;

    private String avatar;
}
