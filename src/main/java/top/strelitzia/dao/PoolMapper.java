package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.model.PoolData;

import java.util.List;

@Repository
public interface PoolMapper {

    List<PoolData> selectPoolByVersion(@Param("version") Integer version);

    List<PoolData> selectAllPool(@Param("current") Integer current);

    Integer selectPoolCount();

    Integer insertPoolData(List<PoolData> poolDatas);
}
