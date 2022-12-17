package top.strelitzia.model;

import lombok.Data;

/**
 * 更新密码用
 */
@Data
public class NewPwd {
    private String oldPwd;

    private String newPwd;
}
