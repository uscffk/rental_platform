package com.ffk.service;

import com.ffk.dao.IRentBeforeBuy;
import com.ffk.pojo.Order;
import com.ffk.pojo.RentBeforeBuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/26 16:03
 */
@Service
public class IRentBeforeBuyServiceImpl implements IOrderService{
    @Autowired
    private IRentBeforeBuy rentBeforeBuy;

    public int addOrder(Order order) {
        return rentBeforeBuy.addOrder(order);
    }

    public int updateOrder(Order order) {
        return rentBeforeBuy.updateOrder(order);
    }

    public List<RentBeforeBuy> queryOrder(Map map) {
        return rentBeforeBuy.queryOrder(map);
    }

    public int deleteOrder(int id) {
        return rentBeforeBuy.deleteOrder(id);
    }
}
