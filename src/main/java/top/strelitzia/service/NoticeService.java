package top.strelitzia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.strelitzia.dao.NoticeMapper;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.models.Info;
import top.strelitzia.models.Notice;
import top.strelitzia.models.UserInfo;
import top.strelitzia.util.TokenUtil;

import java.io.File;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    public List<Notice> getNotice() {
        return noticeMapper.selectAllNotice();
    }
  
    public Info editNotice(String token, Integer noticeId, String text, MultipartFile img) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);

        if (userInfo.getIsAdmin() == 1) {
            String path = null;
            if (img != null) {
                path = fileService.saveFile(img);
            }
            noticeMapper.updateNotice(noticeId, text, path);
            return new Info(true, "修改成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
    
    public Info createNotice(String token, String text, MultipartFile img) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);

        if (userInfo.getIsAdmin() == 1) {
            String path = null;
            if (img != null) {
                path = fileService.saveFile(img);
            } 
            noticeMapper.insertNotice(text, path);
            return new Info(true, "创建成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
    
    public Info deleteNotice(String token, Integer noticeId) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);

        if (userInfo.getIsAdmin() == 1) {
            Notice notice = noticeMapper.selectNoticeById(noticeId);
            new File(notice.getImg()).deleteOnExit();
            noticeMapper.deleteNotice(noticeId);
            return new Info(true, "删除成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
}
