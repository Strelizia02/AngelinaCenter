package top.strelitzia.models;

import lombok.Data;

/**
 * 更详细的个人信息
 */
@Data
public class UserProperty {
    /**
     * 登录id
     */
    private String id;

    /**
     * 拥有的token数量
     */
    private Integer token;

    /**
     * 拥有的下载次数
     */
    private Integer download;

    private String apiKey;
}
