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
    private TokenUtil tokenUtil;

    @Autowired
    private UserMapper userMapper;

    public static final Map<String, Captcha> captchaMap = new HashMap<>();

    public LoginInfo pwdLogin(UserInfo userInfo) {
        //先从登录信息里提取id
        String name = userInfo.getName();
        String id = loginMapper.selectIdByName(name);

        String pwd = rsaUtil.decryptWithPrivate(userInfo.getPwd());
        LoginInfo loginInfo = new LoginInfo();

        //先对密码进行解密操作，然后对比Md5
        if (pwd != null) {
            UserInfo userInfo1 = userMapper.selectUserInfo(id);
            if (pwd.equals(userInfo1.getPwd().getBytes())) {
                loginInfo.setOk(true);
                loginInfo.setToken(tokenUtil.createToken(id));
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
    public LoginInfo captchaLogin(String qq) {
        //先通过qq查到要登录哪个id
        LoginInfo loginInfo = new LoginInfo();
        if (captchaMap.containsKey(qq)) {
            //有没有验证过
            if (captchaMap.get(qq).getIsSend()) {
                String id = loginMapper.selectIdByQq(qq);
                loginInfo.setOk(true);
                loginInfo.setToken(tokenUtil.createToken(id));
                loginInfo.setUserInfo(userMapper.selectUserInfo(id));
                captchaMap.remove(qq)
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
    public Boolean receivedCaptcha(CaptchaRecive captchaRecive) {
        //首先对比qq，id，验证码是否一致
        if (captchaMap.containsKey(captchaRecive.getQq()) 
                && loginMapper.selectIdbyQq(qq).equals(captchaRecive.getId())
                && captchaObj.getCaptcha().equals(captchaRecive.getRaptcha())) {
            Captcha captchaObj = captchaMap.get(captchaRecive.getQq());
            captchaObj.setIsSend(true);
            return true;
        } else {
            return false;
        }
    }

    public String creatCaptcha(String qq) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        captchaMap.put(id, new Captcha(qq, sb.toString()));
        return sb.toString();
    }

    /**
     * 定时删除验证码方法，这里考虑换用redis
     */
    public void removeCaptcha() {
        for (String qq: captchaMap.keySet()) {
            if (System.currentTimeMillis() - captchaMap.get(qq).getTimestamp() >= 60 * 1000) {
                captchaMap.remove(qq);
            }
        }
    }

    public LoginInfo register(UserInfo userInfo) {
        //用户只填写账号密码
        LoginInfo loginInfo = new LoginInfo();
        String id = loginMapper.selectIdByName(userInfo.getName());
        if (id == null) {
            loginInfo.setOk(false);
            loginInfo.setToken("当前用户名已被占用");
            return loginInfo;
        }
        userInfo.setIsRegister(true);
        userInfo.setIsBot(false);
        userInfo.setPwd(DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(userInfo.getPwd())));
        userMapper.insertUserInfo(userInfo);
        loginInfo.setUserInfo(userMapper.selectUserInfoByName(userInfo.getName()));
        loginInfo.setOk(true);
        loginInfo.setToken(tokenUtil.createToken(id));
        return new Info(true, "注册成功");
    }
}
