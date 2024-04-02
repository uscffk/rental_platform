package com.ffk.dao;

import com.ffk.pojo.Manufacturer;
import com.ffk.pojo.ManufacturerProfit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/4 8:54
 */
@Repository
public interface IManufacturerProfit {

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
