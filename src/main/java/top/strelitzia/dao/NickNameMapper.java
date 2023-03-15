package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.model.NickName;

import java.util.List;

@Repository
public interface NickNameMapper {

    List<NickName> selectAllNickName();

    List<NickName> selectNickNameByVersion(@Param("version") Integer version);

    Integer insertNickName(List<NickName> nickName);

    Integer deleteNickName(List<NickName> nickName);
}
