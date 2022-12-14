package top.strelitzia.models;

import lombok.Data;

/**
 * 个人资产信息
 */
@Data
public class UserProperty {
    /**
     * 登录id
     */
    private Integer id;

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
