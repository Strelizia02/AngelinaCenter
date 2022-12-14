package top.strelitzia.models;

import lombok.Data;

/**
 * 接受验证的封装数据
 */
@Data
public class CaptchaReceive {

    private Integer id;

    private String qq;

    private String captcha;
}
