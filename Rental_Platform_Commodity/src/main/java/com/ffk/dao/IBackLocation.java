package com.ffk.dao;

import com.ffk.pojo.BackLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 房发科
 * @create 2022/4/11
 */
@Mapper
@Repository
public interface IBackLocation {
    int addBackLocation(BackLocation backLocation);
    //根据主键id删除
    int deleteBackLocation(@Param("id")int id);
    List<BackLocation> queryAll();
    //按商品id查
    List<BackLocation> queryById(@Param("id")int id);
    int updateBackLocation(BackLocation backLocation);
    //按主键id查询
    BackLocation queryByPkeyId(@Param("id")int id);


}
