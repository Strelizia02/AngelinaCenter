package top.strelitzia.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.strelitzia.models.Function;

import java.util.List;

@Repository
public interface FuncMapper {

    //更新功能被调用的次数
    Integer updateFuncCount(@Param("id") Integer id , Function list);

    //查询所有功能的被调用次数
    List<Function> selectFunctionCount();

    //根据userid去查功能被调用次数（原始数据是每个bot的功能调用次数，这里要根据user和bot的所属关系求和）
    List<Function> selectFunctionCountById(@Param("id") Integer id);
}
