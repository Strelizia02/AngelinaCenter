package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.QQ;

@Repository
public interface QQMapper {

    Integer upsertQqLogin(QQ qq);
}
