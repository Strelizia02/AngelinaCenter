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
}
