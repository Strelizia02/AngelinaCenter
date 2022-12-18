package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.model.UserInfo;

@Repository
public interface UserMapper {

    //查询一个用户的基本信息
    UserInfo selectUserInfo(@Param("id") Integer id);

    //查询一个用户的资产
    UserInfo selectUserProperty(@Param("id") Integer id);

    //新增一个用户
    Integer insertUserInfo(UserInfo userInfo);

    //根据名字查用户id
    Integer selectIdByName(@Param("name") String name);

    //根据id查密码
    String selectPwdById(@Param("id") Integer id);

    //更新用户名
    Integer updateName(@Param("id") Integer id, @Param("name") String name);

    //更新用户名
    Integer updatePwd(@Param("id") Integer id, @Param("pwd") String pwd);

    //根据名字查用户基本信息
    UserInfo selectUserInfoByName(@Param("name") String name);

    //根据qq查询用户
    Integer selectIdByQq(@Param("qq") String qq);

    //查询一个用户的token余额
    Integer selectTokenByBotId(@Param("botId") String botId);

    //更新一个用户的token余额（入参是botId，需要嵌套查询）
    Integer updateTokenByBotId(@Param("botId") String botId, @Param("num") Long num);

    //更新一个用户的token已使用量
    Integer updateUseTokenByBotId(@Param("botId") String botId, @Param("num") Long num);

    //更新一个用户的调用次数
    Integer updateCountByBotId(@Param("botId") String botId, @Param("num") Long num);
}
