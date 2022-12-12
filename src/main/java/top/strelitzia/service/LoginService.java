package top.strelitzia.service;

import org.springframework.stereotype.Service;
import top.strelitzia.models.LoginInfo;

@Service
public class LoginService {

    public LoginInfo pwdLogin(LoginInfo loginInfo) {
        return null;
    }

    public LoginInfo captchaLogin(LoginInfo loginInfo) {
        return null;
    }

    public String creatCaptcha(LoginInfo loginInfo) {
        return "true";
    }

    public LoginInfo register(LoginInfo loginInfo) {
        return null;
    }
}
