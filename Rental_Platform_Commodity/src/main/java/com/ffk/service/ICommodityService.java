package com.ffk.service;

import com.ffk.pojo.Commodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/18 10:11
 */
public interface ICommodityService {

    /**
     * 添加商品
     * @param commodity
     * @return
     */
    int addCommodity(Commodity commodity) throws Exception;

    /**
     * 根据id删除商品（下架商品）
     * @param id
     * @return
     */
    int deleteCommodity(int id);

    /**
     * 更新商品信息
     * @param commodity
     * @return
     */
    int updateCommodity(Commodity commodity);

    /**
     * 查询商品
     * @param map 传入map实现多条件查询
     * @return
     */
    List<Commodity> queryCommodity(Map map);

    /**
     *
     * @param id
     * @return
     */
    Commodity queryById(int id);

    /**
     * 查询所有拍卖合约地址
     * @return
     */
    List<String> getAllAuctionAddress();

    /**
     * 获取商品总数量
     * @return
     */
    int getTotal();

    /**
     * 返回一个商家id的所有商品id
     * @param mid
     * @return
     */
    List<Integer> queryCommodityIdByMId(@Param("mid") int mid);

}
