package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BotMapper {
    String selectBotIdByQq(@Param("qq") String qq);

    Integer updateUser(@Param("user_id") Integer userId, @Param("id") String botId);
}
