package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.UserInfo;

@Repository
public interface UserMapper {

    UserInfo selectUserInfo(@Param("id") Integer id);

    UserInfo selectUserProperty(@Param("id") Integer id);

    Integer insertUserInfo(UserInfo userInfo);

    Integer selectIdByName(@Param("name") String name);

    String selectPwdById(@Param("id") Integer id);

    Integer updateName(@Param("id") Integer id, @Param("name") String name);

    UserInfo selectUserInfoByName(@Param("name") String name);
    Integer selectIdByQq(@Param("qq") String qq);
}
