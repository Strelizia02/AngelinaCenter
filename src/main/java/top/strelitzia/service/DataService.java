package top.strelitzia.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.strelitzia.dao.BotMapper;
import top.strelitzia.dao.FuncMapper;
import top.strelitzia.dao.QQMapper;
import top.strelitzia.model.*;
import top.strelitzia.util.TokenUtil;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
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
        log.info(bot.toString());
        if (bot.getId() == null) {
            UUID uuid = UUID.randomUUID();
            bot.setId(uuid.toString());
        }
        botMapper.upsertBot(bot.getId(), bot.getName());
        for (QQ info: bot.getList()) {
            if (info.getIsOnline()) {
                QQ qq = new QQ();
                qq.setQq(info.getQq());
                qq.setFrame(info.getFrame());
                qq.setType(info.getType());
                qq.setBotId(bot.getId());
                qq.setLoginTime(System.currentTimeMillis());
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
    
    public Boolean pushData(PushData pushData) {
        log.info(pushData.toString());
        for (Function f: pushData.getFunctionCount()) {
            funcMapper.upsertFuncCount(pushData.getBotId(), f.getName(), f.getCount());
        }

        Bot bot = new Bot();
        bot.setId(pushData.getBotId());
        bot.setRam(pushData.getTotalMemory());
        Long send = 0L;
        Long receive = 0L;
        for (MessageCount messageCount: pushData.getMessageCount()) {
            if (messageCount.getType() == 3) {
                send += messageCount.getCount();
            } else {
                receive += messageCount.getCount();
            }
        }
        bot.setSend(send);
        bot.setReceive(receive);
        bot.setQqCount(pushData.getQqCount());
        bot.setGroupCount(pushData.getGroupCount());

        botMapper.updateBotInfo(bot);

        return true;
    }
    
    public List<Function> getFuncList() {
        return funcMapper.selectFunctionCount();
    }
    
    public List<Function> getSomeOneFuncList(String token) {
        Integer id = tokenUtil.getTokenId(token);
        return funcMapper.selectFunctionCountById(id);
    }
    
    public List<PoolData> getPoolData(String botId, String version) {
        List<String> botIds = botMapper.selectAllBotId();
        if (botIds.contains(botId)) {
            botMapper.updateBotDownload();
            List<PoolData> datas = poolMapper.selectpoolByVersion(version);
            return List<PoolData>;
        }
        return null;
    }
}
