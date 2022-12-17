package top.strelitzia.dao;

import org.springframework.stereotype.Repository;
import top.strelitzia.model.QQ;

@Repository
public interface QQMapper {

    //更新一个qq的登录时间和基础信息（有就更新，没有就插入）
    Integer upsertQqLogin(QQ qq);
}
