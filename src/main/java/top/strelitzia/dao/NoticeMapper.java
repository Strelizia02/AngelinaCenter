package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.Notice;

import java.util.List;

@Repository
public interface NoticeMapper {

    Integer insertNotice(@Param("text") String text, @Param("path") String path);

    List<Notice> selectAllNotice();

    Integer updateNotice(@Param("noticeId") Integer noticeId, @Param("text") String text, @Param("path") String path);

    Notice selectNoticeById(@Param("noticeId") Integer noticeId);

    Integer deleteNotice(@Param("noticeId") Integer noticeId);
}
