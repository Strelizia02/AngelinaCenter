package top.strelitzia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.strelitzia.dao.BotMapper;
import top.strelitzia.dao.FuncMapper;
import top.strelitzia.dao.QQMapper;
import top.strelitzia.models.*;
import top.strelitzia.util.TokenUtil;

import java.util.List;
import java.util.UUID;

@Service
public class DataService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BotMapper botMapper;

    @Autowired
    private QQMapper qqMapper;

    @Autowired
    private FuncMapper funcMapper;
    
    public String heartBeats(Bot bot) {
        if (bot.getId() == null) {
            UUID uuid = UUID.randomUUID();
            bot.setId(uuid.toString());
        }
        for (QQ info: bot.getList()) {
            if (info.getIsOnline()) {
                QQ qq = new QQ();
                qq.setQq(info.getQq());
                qq.setFrame(info.getFrame());
                qq.setType(info.getType());
                qq.setBotId(bot.getId());
                qqMapper.upsertQqLogin(qq);
            }
        }
        return bot.getId();
    }
    
    public List<Bot> getBotList(String token) {
        Integer id = tokenUtil.getTokenId(token);
        return botMapper.selectBotById(id);
    }
    
    public BotData getBotBoard() {
        return botMapper.selectCountBot();
    }
    
    public Boolean pushData(String token, List<Function> list) {
        Integer id = tokenUtil.getTokenId(token);
        funcMapper.updateFuncCount(id ,list);
        return true;
    }
    
    public List<Function> getFuncList() {
        return funcMapper.selectFunctionCount();
    }
    
    public List<Function> getSomeOneFuncList(String token) {
        Integer id = tokenUtil.getTokenId(token);
        return funcMapper.selectFunctionCountById(id);
    }
}
