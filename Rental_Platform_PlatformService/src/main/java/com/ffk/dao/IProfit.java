package com.ffk.dao;

import com.ffk.pojo.Profit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/3 23:47
 */
@Repository
public interface IProfit {
    /**
     * 增加收益记录
     * @param profit
     * @return
     */
    int addProfit(Profit profit);

    /**
     * 查询收益
     * @param map
     * @return
     */
    List<Profit> queryProfit(Map map);
}
