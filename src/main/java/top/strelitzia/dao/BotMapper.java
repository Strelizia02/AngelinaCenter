package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.model.Bot;
import top.strelitzia.model.BotData;

import java.util.List;

@Repository
public interface BotMapper {
    //根据qq号查询Bot的id
    String selectBotIdByQq(@Param("qq") String qq);

    //修改bot所属的userId
    Integer updateUser(@Param("user_id") Integer userId, @Param("id") String botId);

    //根据userid查询下属Bot
    List<Bot> selectBotById(@Param("id") Integer id);

    //根据userid查询下属Bot
    Integer selectUserIdByBotId(@Param("botId") String botId);

    //查询所有qq的在线离线总数
    BotData selectCountBot();

    Integer upsertBot(@Param("id") String id, @Param("name") String name);

    Integer updateBotInfo(Bot bot);

    List<String> selectAllBotId();

    Integer updateBotDownload(@Param("botId") String botId);
}
