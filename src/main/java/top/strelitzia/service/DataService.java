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
        return list;
    }
}
