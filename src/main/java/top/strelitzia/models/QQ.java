package top.strelitzia.models;

import lombok.Data;

/**
 * QQ的字段封装
 */
@Data
public class QQ {

    private String qq;

    private String frame;

    private String type;

    private Long loginTime;
        
    private Boolean isOnline;

    private String botId;

    private Integer userId;
}
