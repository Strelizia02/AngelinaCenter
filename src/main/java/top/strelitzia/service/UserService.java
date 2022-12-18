package top.strelitzia.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.strelitzia.dao.BotMapper;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.model.Bot;
import top.strelitzia.model.NewPwd;
import top.strelitzia.model.UserInfo;
import top.strelitzia.util.RSAUtil;
import top.strelitzia.util.TokenUtil;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private RSAUtil rsaUtil;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BotMapper botMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public UserInfo getUserProperty(String token) {
        Integer id = tokenUtil.getTokenId(token);
        return userMapper.selectUserProperty(id);
    }
    
    public UserInfo getUserInfo(String token) {
        Integer id = tokenUtil.getTokenId(token);
        return userMapper.selectUserInfo(id);
    }
    
    public Boolean addUserBot(String token, String qq) {
        Integer id = tokenUtil.getTokenId(token);
        String botId = botMapper.selectBotIdByQq(qq);
        if (redisTemplate.hasKey(qq)) {
            JSONObject obj = new JSONObject(redisTemplate.opsForValue().get(qq));
            if (obj.getBoolean("isSend")) {
                //有没有验证过
                botMapper.updateUser(id, botId);
                redisTemplate.delete(qq);
                return true;
            }
        }
        return false;
    }

    public Boolean removeUserBot(String token, String botId) {
        Integer id = tokenUtil.getTokenId(token);
        Integer user = botMapper.selectUserIdByBotId(botId);
        if (user.equals(id)) {
            botMapper.updateUser(null, botId);
            return true;
        }
        return false;
    }
    
    public Boolean editUserName(String token, String name) {
        Integer id = tokenUtil.getTokenId(token);
        Integer other = userMapper.selectIdByName(name);
        if (other == null) {
            userMapper.updateName(id, name);
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean editUserPwd(String token, NewPwd pwd) {
        Integer id = tokenUtil.getTokenId(token);
        if (userMapper.selectPwdById(id).equals(DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(pwd.getOldPwd()).getBytes()))) {
            userMapper.updatePwd(id, DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(pwd.getNewPwd()).getBytes()));
            return true;
        } else {
            return false;
        }
    }
}
