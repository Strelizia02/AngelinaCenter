package top.strelitzia.model;

import lombok.Data;

/**
 * 登录验证码的结构
 */
@Data
public class Captcha {
    private String captcha;

    private String id;

    private Boolean isSend;

    public Captcha(String id, String captcha) {
        this.captcha = captcha;
        this.id = id;
        this.isSend = false;
    }
}
