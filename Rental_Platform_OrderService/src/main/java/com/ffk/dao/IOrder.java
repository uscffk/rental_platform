package com.ffk.dao;

import com.ffk.pojo.Order;
import com.ffk.pojo.RentBeforeBuy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/26 16:16
 */
@Repository
public interface IOrder {
    /**
     * 新增订单
     * @param order
     * @return
     */
    int addOrder(Order order);

    /**
     * 修改订单
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 查询订单
     * @param map
     * @return
     */
    List<RentBeforeBuy> queryOrder(Map map);

    /**
     * 删除订单
     * @param id
     * @return
     */
    int deleteOrder(int id);
}
