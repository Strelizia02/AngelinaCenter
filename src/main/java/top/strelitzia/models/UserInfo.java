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
     * 名下Bot列表
     */
    private List<String> botList;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String name;
}
