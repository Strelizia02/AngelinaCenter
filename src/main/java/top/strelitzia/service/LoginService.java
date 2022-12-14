package top.strelitzia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.models.Captcha;
import top.strelitzia.models.LoginInfo;
import top.strelitzia.models.UserInfo;
import top.strelitzia.util.RSAUtil;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private RSAUtil rsaUtil;

    @Autowired
    private UserMapper userMapper;

    public static final Map<String, Captcha> captchaMap = new HashMap<>();

    public LoginInfo pwdLogin(UserInfo userInfo) {
        String id = userInfo.getId();
        String pwd = rsaUtil.decryptWithPrivate(userInfo.getPwd());
        LoginInfo loginInfo = new LoginInfo();

        if (pwd != null) {
            UserInfo userInfo1 = userMapper.selectUserInfo(id);
            if (pwd.equals(DigestUtils.md5DigestAsHex(userInfo1.getPwd().getBytes()))) {
                loginInfo.setOk(true);
                loginInfo.setToken();
                loginInfo.setUserInfo(userInfo1);
            } else {
                loginInfo.setOk(false);
            }
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
            if (captchaMap.get(id).getIsSend()) {
                loginInfo.setOk(true);
                loginInfo.setUserInfo(userMapper.selectUserInfo(id));
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
                captchaObj.setIsSend(true);
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

        captchaMap.put(id, new Captcha(id, sb.toString()));

        return sb.toString();
    }

    /**
     * 删除验证码方法
     */
    public void removeCaptcha() {
        for (String id: captchaMap.keySet()) {
            if (System.currentTimeMillis() - captchaMap.get(id).getTimestamp() >= 60 * 1000) {
                captchaMap.remove(id);
            }
        }
    }

    public UserInfo register(UserInfo userInfo) {
        userMapper.insertUserInfo(userInfo);
        return userInfo;
    }
}
