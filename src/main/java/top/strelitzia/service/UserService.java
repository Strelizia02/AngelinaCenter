package top.strelitzia.service;

import org.springframework.stereotype.Service;
import top.strelitzia.models.LoginInfo;
import top.strelitzia.models.UserProperty;

@Service
public class UserService {
    
    @Autowired
    private RSAUtil rsaUtil;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TokenUtil tokenUtil;

    public UserProperty getUserProperty(String token) {
        String id = tokenUtil.getTokenId(token);
        UserProperty userproperty = userMapper.selectUserProperty(id);
        return userproperty;
    }
    
    public UserInfo getUserInfo(String token) {
        String id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);
        return userInfo;
    }
    
    public Boolean editUserBot(String token, String qq) {
        String id = tokenUtil.getTokenId(token);
        String botId = botMapper.selectBotIdByQq(qq);
        if (LoginService.captchaMap.containsKey(qq) && LoginService.captchaMap.get(qq).getIsSend()) {
            //有没有验证过
            UserInfo.insertBot(id, qq)
            return true;
        }
        return false;
    }
    
    public Boolean editUserName(String token, String name) {
        String id = tokenUtil.getTokenId(token);
        String other = loginMapper.seleceIdByName(name);
        if (other == null) {
            userMapper.updateName(id, name);
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean editUserPwd(String token, NewPwd pwd) {
        String id = tokenUtil.getTokenId(token);
        if (userMapper.selectPwdById(id).equals(pwd.getOldPwd())) {
            userMapper.updateName(id, DigestUtils.md5DigestAsHex(rsaUtil.decryptWithPrivate(pwd.getNewPwd())));
            return true;
        } else {
            return false;
        }
    }
}
