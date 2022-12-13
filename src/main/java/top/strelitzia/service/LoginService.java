package top.strelitzia.service;

import org.springframework.stereotype.Service;
import top.strelitzia.models.Captcha;
import top.strelitzia.models.LoginInfo;
import top.strelitzia.models.UserInfo;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    public static final Map<String, Captcha> captchaMap = new HashMap<>();

    public LoginInfo pwdLogin(UserInfo userInfo) {
        String id = userInfo.getId();
        String pwd = userInfo.getPwd();

        UserInfo userInfo1 = loginMapper.selectUserInfo(id);

        LoginInfo loginInfo = new LoginInfo();
        if (pwd != null && pwd.equals(DigestUtils.md5DigestAsHex(userInfo1.getPwd().getBytes()))) {
            loginInfo.setOk(true);
            loginInfo.setUserInfo(userInfo1);
        } else {
            loginInfo.setOk(false);
        }
        return loginInfo;
    }

    /**
     * 查看有没有发验证码
     */
    public LoginInfo captchaLogin(String id) {
        LoginInfo loginInfo = new LoginInfo();
        if (captchaMap.containsKey(id)) {
            if (captchaMap.get(id).getSend()) {
                loginInfo.setOk(true);
                loginInfo.setUserInfo(loginMapper.selectUserInfo(id));
            } else {
                loginInfo.setOk(false);
            }
        } else {
            loginInfo.setOk(false);
        }
        return loginInfo;
    }

    /**
     * 接受验证码方法
     */
    public Boolean receivedCaptcha(String id, String captcha) {
        if (captchaMap.containsKey(id)) {
            Captcha captchaObj = captchaMap.get(id);

            if (captchaObj.getCaptcha().equals(captcha)) {
                captchaObj.setSend(true);
            }
            return true;
        } else {
            return false;
        }
    }

    public String creatCaptcha(String id) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        captcha.put(id, new Captcha(id, sb.toString()));

        return sb.toString();
    }

    /**
     * 删除验证码方法
     */
    public Boolean removeCaptcha() {
        captchaMap.removeIf(captcha.getTimestamp() - System.currentTimeMillis() / 1000 / 60 >= 10);
    }



    public UserInfo register(UserInfo userInfo) {
        loginMapper.insertUserInfo(userInfo);
        return userInfo;
    }
}
