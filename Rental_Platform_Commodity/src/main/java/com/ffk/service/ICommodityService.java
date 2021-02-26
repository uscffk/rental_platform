package com.ffk.service;

import com.ffk.pojo.Commodity;

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
    int addCommodity(Commodity commodity);

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    int deleteCommodity(int id);

    /**
     * 更新商品
     * @param commodity
     * @return
     */
    int updateCommodity(Commodity commodity);

    /**
     * 查询商品
     * @param map
     * @return
     */
    List<Commodity> queryCommodity(Map map);
}
