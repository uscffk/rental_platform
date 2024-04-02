package com.ffk.service;

import com.ffk.pojo.BackLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 房发科
 * @create 2022/4/11
 */
public interface IBackLocationService {
    int addBackLocation(BackLocation backLocation);
    int deleteBackLocation(@Param("id")int id);
    List<BackLocation> queryAll();
    //按商品id查
    List<BackLocation> queryById(@Param("id")int id);
    int updateBackLocation(BackLocation backLocation);
    //按主键id查
    BackLocation queryByPkeyId(@Param("id")int id);

    List<BackLocation> queryByMId(int mid);
}
