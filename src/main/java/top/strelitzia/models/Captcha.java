package top.strelitzia.models;

import lombok.Data;

@Data
public class Captcha {
    private String captcha;

    private String id;

    private Boolean isSend;

    private Long timestamp;

    public Captcha(String id, String captcha) {
        this.captcha = captcha;
        this.id = id;
        this.isSend = false;
    }
}
