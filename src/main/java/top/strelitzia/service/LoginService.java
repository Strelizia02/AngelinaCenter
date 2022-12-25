package top.strelitzia.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.strelitzia.dao.BotMapper;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.model.*;
import top.strelitzia.util.RSAUtil;
import top.strelitzia.util.TokenUtil;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private RSAUtil rsaUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BotMapper botMapper;

    public LoginInfo pwdLogin(UserInfo userInfo) {
        //先从登录信息里提取id
        String name = userInfo.getName();
        Integer id = userMapper.selectIdByName(name);
//        String pwd = userInfo.getPwd();
        LoginInfo loginInfo = new LoginInfo();

        //先对密码进行解密操作，然后对比Md5
        if (userInfo.getPwd() != null) {
            String pwd = DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(userInfo.getPwd()).getBytes());
            String pwd1 = userMapper.selectPwdById(id);
            if (pwd.equals(pwd1)) {
                loginInfo.setOk(true);
                loginInfo.setToken(tokenUtil.createToken(id));
                loginInfo.setUserInfo(userMapper.selectUserInfo(id));
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
        Integer id = userMapper.selectIdByQq(qq);
        if (id == null) {
            loginInfo.setOk(false);
            loginInfo.setToken("当前Bot未绑定账号，请先注册账号，在用户中心中绑定Bot");
            return loginInfo;
        }
        if (redisTemplate.hasKey(qq)) {
            JSONObject obj = new JSONObject(redisTemplate.opsForValue().get(qq));
            if (obj.getBoolean("isSend")) {
                //有没有验证过
                loginInfo.setOk(true);
                loginInfo.setToken(tokenUtil.createToken(id));
                loginInfo.setUserInfo(userMapper.selectUserInfo(id));
                redisTemplate.delete(qq);
                return loginInfo;
            }
        }
        loginInfo.setOk(false);
        return loginInfo;
    }

    /**
     * 接受验证码方法
     */
    public Boolean receivedCaptcha(CaptchaReceive captchaReceive) {
        //对比qq，botId,验证码是否一致
        if (redisTemplate.hasKey(captchaReceive.getQq())) {
            if (botMapper.selectBotIdByQq(captchaReceive.getQq()).equals(captchaReceive.getBotId())) {
                JSONObject obj = new JSONObject(redisTemplate.opsForValue().get(captchaReceive.getQq()));
                if (obj.getString("captcha").equals(captchaReceive.getCaptcha())) {
                    obj.put("isSend", true);
                    redisTemplate.opsForValue().set(captchaReceive.getQq(), obj.toString());
                    return true;
                }
            }
        }
        return false;
    }

    public String creatCaptcha(String qq) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        JSONObject obj = new JSONObject();
        obj.put("isSend", false);
        obj.put("captcha", sb.toString());

        redisTemplate.opsForValue().set(qq, obj.toString(), 1, TimeUnit.MINUTES);
        return sb.toString();
    }

    public LoginInfo register(UserInfo userInfo) {
        //用户只填写账号密码
        LoginInfo loginInfo = new LoginInfo();
        Integer id = userMapper.selectIdByName(userInfo.getName());
        if (id != null) {
            loginInfo.setOk(false);
            loginInfo.setToken("当前用户名已被占用");
            return loginInfo;
        }
        userInfo.setPwd(DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(userInfo.getPwd()).getBytes()));
        userMapper.insertUserInfo(userInfo);
        id = userMapper.selectIdByName(userInfo.getName());
        loginInfo.setUserInfo(userMapper.selectUserInfoByName(userInfo.getName()));
        loginInfo.setOk(true);
        loginInfo.setToken(tokenUtil.createToken(id));
        return loginInfo;
    }
}
