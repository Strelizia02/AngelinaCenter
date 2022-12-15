package top.strelitzia.service;

import org.springframework.stereotype.Service;
import top.strelitzia.models.UserInfo;

@Service
public class DataService {

    public List<Notice> getNotice() {
      List<Notice> list = noticeMapper.selectAllNotice();  
      return list;
    }
  
    public Info editNotice(String token, Integer id, String text, MultipartFile img) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserById(id); 

        if (userInfo.getIsAdmin()) {
            if (userInfo.getImg != null) {
                String path = fileService.saveFile(Img);
            } 
            noticeMapper.updateNotice(id, text, img);
            return new Info(true, "修改成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
    
    public Info createNotice(String token, String text, MultipartFile img) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserById(id); 

        if (userInfo.getIsAdmin()) {
            if (userInfo.getImg != null) {
                String path = fileService.saveFile(Img);
            } 
            noticeMapper.insertNotice(text, img);
            return new Info(true, "创建成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
    
    public Info deleteNotice(String token, Integer noticeId) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserById(id); 

        if (userInfo.getIsAdmin()) {
            Notice notice = noticeMapper.selectNoticeById(noticeId);  
            new File(notice.getImg()).deleteFile();
            noticeMapper.deleteNotice(noticeId);
            return new Info(true, "删除成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
    
    public String heartBeats(Bot bot) {
        if (bot.getId() == null) {
            UUID uuid = UUID.random();
            bot.setId(uuid);
        }
        for (QQInfo info: bot.getQq()) {
            if (info.getIsOnline()) {
                QQ qq = new QQ();
                qq.setQq(info.getQq());
                qq.setFrame(info.getFrame());
                qq.setType(info.getType());
                qq.setBotId(bot.getId());
                qQMapper.upsertQqLogin(qq);
            }
        }
        return bot.getId();
    }
    
    public String heartBeats(Bot bot) {
        if (bot.getId() == null) {
            UUID uuid = UUID.random();
            bot.setId(uuid);
        }
        for (QQInfo info: bot.getQq()) {
            if (info.getIsOnline()) {
                QQ qq = new QQ();
                qq.setQq(info.getQq());
                qq.setFrame(info.getFrame());
                qq.setType(info.getType());
                qq.setBotId(bot.getId());
                qQMapper.upsertQqLogin(qq);
            }
        }
        return bot.getId();
    }
    
    public List<Bot> getBotList(String token) {
        Integer id = tokenUtil.getTokenId(token);
        List<Bot> list = botMapper.selectBotById(id);
        return list;
    }
    
    public BotData getBotBoard() {
        BotData data = botMapper.selectCountBot();
        return data;
    }
    
    public Boolean pushData(String token, List<Function> list) {
        Integer id = tokenUtil.getTokenId(token);
        botMapper.updateFuncCount(id ,list);
    }
    
    public List<Function> getFuncList() {
        List<Function> list = functionMapper.selectFunctionCount();
        return list;
    }
    
    public List<Function> getSomeOneFuncList(String token) {
        Integer id = tokenUtil.getTokenId(token);
        List<Function> list = functionMapper.selectFunctionCountbyId(id);
        return list;
    }
}
