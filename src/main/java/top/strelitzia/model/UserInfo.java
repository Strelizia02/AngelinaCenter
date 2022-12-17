package top.strelitzia.model;

import lombok.Data;

import java.util.List;

/**
 * 登录信息封装
 */
@Data
public class UserInfo {
    /**
     * 登录id
     */
    private Integer id;
    /**
     * 登录密码
     */
    private String pwd;
    /**
     * 名下Bot列表
     */
    private List<Bot> botList;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    /**
     * 用户名
     */
    private String name;

    /**
     * 拥有的token数量
     */
    private Integer token;

    /**
     * 已消耗的token
     */
    private Integer useToken;

    /**
     * 已调用次数
     */
    private Integer count;
}
