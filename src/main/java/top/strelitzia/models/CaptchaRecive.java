package top.strelitzia.models;

import lombok.Data;

/**
 * 接受验证的封装数据
 */
@Data
public class CaptchaRecive {

    private String id;

    private String qq;

    private String captcha;
}
