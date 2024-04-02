package com.ffk.service;

import com.ffk.pojo.ManufacturerProfit;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/4 8:54
 */
public interface IManufacturerProfitService {
    /**
     * 添加商品收益
     * @param manufacturerProfit
     * @return
     */
    int addManufacturerProfit(ManufacturerProfit manufacturerProfit);

    /**
     * 查询商家收益
     * @param map
     * @return
     */
    List<ManufacturerProfit> queryManufacturerProfit(Map map);
}
