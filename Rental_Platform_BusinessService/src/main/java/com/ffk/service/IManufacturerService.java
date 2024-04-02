package com.ffk.service;

import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Manufacturer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/3 15:20
 */
public interface IManufacturerService {
    /**
     * 查询厂商
     * @param map
     * @return
     */
    List<Manufacturer> queryManufacturer(Map map) throws Exception;

    /**
     * 按Id查询
     * @param manufacturerId
     * @return
     */
    Manufacturer queryManufacturerById(@Param("manufacturerId") int manufacturerId) throws Exception;

    /**
     * 增加厂商
     * @param manufacturer
     * @return
     */
    int addManufacturer(Manufacturer manufacturer) throws Exception;

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
     * 查询余额
     * @param contractAddress
     * @return
     */
    int queryBalance(int id) throws Exception;

    /**
     * 充值
     * @param username
     * @param amount
     * @return
     */
    CommonResult recharge(String username,float amount);

    /**
     * 提现
     * @param username
     * @param amount
     * @return
     */
    CommonResult getRMB(String username,float amount);

    /**
     * 查询反馈
     * @return
     */
    CommonResult queryFeedBack();

    /**
     * 更新厂商
     * @param manufacturer
     * @return
     */
    int updateManufacturer(Manufacturer manufacturer);




    /**
     * 查询自家商品
     * @param map
     * @return
     */
    CommonResult queryCommodity(Map map);

    /**
     * 增加商品
     * @param commodity
     * @return
     */
    CommonResult addCommodity(Commodity commodity);

    /**
     * 更新商品
     * @param commodity
     * @return
     */
    CommonResult updateCommodity(Commodity commodity);

    /**
     * 查询用户
     * @param map
     * @return
     */
    CommonResult queryUsers(Map map);


    /**
     * 统计收益
     * @param map
     * @return
     */
    CommonResult queryProfit(Map map);


    //统计商品租赁人群分布

    /**
     * 获得商家总数
     * @return
     */
    int getTotal();


}
