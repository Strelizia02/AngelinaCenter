package top.strelitzia.models;

import lombok.Data;

/**
 * 功能统计数量
 */
@Data
public class Function {

    private String name;

    private String botId;

    private Long count;
}
