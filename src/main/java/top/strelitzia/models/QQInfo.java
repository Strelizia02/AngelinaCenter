package top.strelitzia.models;

import lombok.Data;

@Data
public class QQInfo {

    private String qq;

    private String frame;

    private String type;

    private Boolean isOnline;
    
    private Long timestamp;
}
