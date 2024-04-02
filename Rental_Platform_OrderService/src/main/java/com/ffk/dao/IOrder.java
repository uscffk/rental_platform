package com.ffk.dao;

import com.ffk.pojo.Order;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/23 9:31
 */
@Repository
public interface IOrder {
    /**
     * 更新订单
     * @param orders
     * @return
     */
    int  updateOrder(Order orders);

    /**
     * 查询订单
     * @param map
     * @return
     */
    List<Order> queryOrder(Map map);

    /**
     * 新增订单
     * @param orders
     * @return
     */
    int addOrder(Order orders);

    /**
     * 按id查询订单
     * @param id
     * @return
     */
    Order queryById(int id);




}
