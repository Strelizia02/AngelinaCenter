package top.strelitzia.model;

import lombok.Data;

/**
 * 用于封装Bot的在线情况
 */
@Data
public class BotData {

    private Integer online;

    private Integer offline;
}
