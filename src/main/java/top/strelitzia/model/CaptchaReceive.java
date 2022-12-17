package top.strelitzia.model;

import lombok.Data;

/**
 * 接受验证的封装数据
 */
@Data
public class CaptchaReceive {
    private String qq;

    private String captcha;
}
