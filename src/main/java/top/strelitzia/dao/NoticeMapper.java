package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.model.Notice;

import java.util.List;

@Repository
public interface NoticeMapper {

    //新增一条公告
    Integer insertNotice(@Param("text") String text, @Param("path") String path);

    //查询所有公告
    List<Notice> selectAllNotice();

    //更新一条公告
    Integer updateNotice(@Param("noticeId") Integer noticeId, @Param("text") String text, @Param("path") String path);

    //查询一条公告
    Notice selectNoticeById(@Param("noticeId") Integer noticeId);

    //查询一条公告
    String selectNoticeImgById(@Param("noticeId") Integer noticeId);

    //删除一条公告
    Integer deleteNotice(@Param("noticeId") Integer noticeId);
}
