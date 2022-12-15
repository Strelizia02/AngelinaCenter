package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.Bot;
import top.strelitzia.models.BotData;

import java.util.List;

@Repository
public interface BotMapper {
    String selectBotIdByQq(@Param("qq") String qq);

    Integer updateUser(@Param("user_id") Integer userId, @Param("id") String botId);

    List<Bot> selectBotById(@Param("id") Integer id);

    BotData selectCountBot();
}
