package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitMapper {
    Integer insertVisit(@Param("url") String url, @Param("time") Long time);

    List<Integer> selectVisitByTime();

    List<Integer> selectVisitByUrl();
}
