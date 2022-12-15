package top.strelitzia.models;

import lombok.Data;

/**
 * 返回信息
 */
@Data
public class Info {

    private Boolean ok;

    private String text;

    public Info(Boolean ok, String text) {
        this.ok = ok;
        this.text = text;
    }
}
