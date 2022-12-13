package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import top.strelitzia.models.UserInfo;

public interface UserMapper {

    UserInfo selectUserInfo(@Param("id") String id);

    Integer insertUserInfo(UserInfo userInfo);
}
