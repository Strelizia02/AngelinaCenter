package top.strelitzia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.strelitzia.dao.NoticeMapper;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.model.Info;
import top.strelitzia.model.Notice;
import top.strelitzia.model.UserInfo;
import top.strelitzia.util.TokenUtil;

import java.io.*;
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

    public byte[] getNoticeImg(Integer noticeId){
        String path = noticeMapper.selectNoticeImgById(noticeId);
        try (FileInputStream fs = new FileInputStream(path)){
            byte[] b = new byte[fs.available()];
            //将字节流中的数据传递给字节数组
            fs.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
  
    public Info editNotice(String token, Integer noticeId, String text, MultipartFile img) {
        Integer id = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(id);

        if (userInfo.getIsAdmin() == 1) {
            String path = null;
            if (img != null) {
                String old = noticeMapper.selectNoticeImgById(noticeId);
                new File(old).deleteOnExit();
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
            String img = noticeMapper.selectNoticeImgById(noticeId);
            if (img != null) {
                new File(img).deleteOnExit();
            }
            noticeMapper.deleteNotice(noticeId);
            return new Info(true, "删除成功");
        } else {
            return new Info(false, "您没有权限");
        }
    }
}
