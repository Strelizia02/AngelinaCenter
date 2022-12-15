package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.Function;

import java.util.List;

@Repository
public interface FuncMapper {

    Integer updateFuncCount(@Param("id") Integer id , List<Function> list);

    List<Function> selectFunctionCount();

    List<Function> selectFunctionCountById(@Param("id") Integer id);
}
