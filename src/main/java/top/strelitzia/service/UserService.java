package top.strelitzia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.strelitzia.dao.BotMapper;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.models.NewPwd;
import top.strelitzia.models.UserInfo;
import top.strelitzia.util.RSAUtil;
import top.strelitzia.util.TokenUtil;

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

    public UserInfo getUserProperty(String token) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserProperty(id);
        return userInfo;
    }
    
    public UserInfo getUserInfo(String token) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);
        return userInfo;
    }
    
    public Boolean addUserBot(String token, String qq) {
        Integer id = tokenUtil.getTokenId(token);
        String botId = botMapper.selectBotIdByQq(qq);
        if (LoginService.captchaMap.containsKey(id) && LoginService.captchaMap.get(id).getIsSend()) {
            botMapper.updateUser(id, botId);
            return true;
        }
        return false;
    }

    public Boolean removeUserBot(String botId) {
        botMapper.updateUser(null, botId);
        return true;
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
        if (userMapper.selectPwdById(id).equals(pwd.getOldPwd())) {
            userMapper.updateName(id, DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(pwd.getNewPwd()).getBytes()));
            return true;
        } else {
            return false;
        }
    }
}
