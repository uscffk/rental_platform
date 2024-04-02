package com.ffk.dao;

import com.ffk.pojo.Manufacturer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/3 15:17
 */
@Mapper
@Repository
public interface IManufacturer {
    /**
     * 查询厂商
     * @param map
     * @return
     */
    List<Manufacturer> queryManufacturer(Map map);

    /**
     * 按Id查询
     * @param manufacturerId
     * @return
     */
    Manufacturer queryManufacturerById(@Param("manufacturerId") int manufacturerId);
    /**
     * 更新厂商
     * @param manufacturer
     * @return
     */
    int updateManufacturer(Manufacturer manufacturer);

    /**
     * 增加厂商
     * @param manufacturer
     * @return
     */
    int addManufacturer(Manufacturer manufacturer);

    /**
     * 登录查询
     * @param username
     * @return
     */
    Manufacturer queryLogin(String username);

    /**
     * 根据厂商id删除厂商
     * @param id
     * @return
     */
    int deleteManufacturer(int id);

    /**
     * 获得商家总数
     * @return
     */
    int getTotal();
}
