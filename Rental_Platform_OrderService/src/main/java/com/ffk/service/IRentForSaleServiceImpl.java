package com.ffk.service;

import com.ffk.dao.IRentForSale;
import com.ffk.pojo.Order;
import com.ffk.pojo.RentBeforeBuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/26 16:04
 */
@Service
public class IRentForSaleServiceImpl implements IOrderService{
    @Autowired
    private IRentForSale rentForSale;

    public int addOrder(Order order) {
        return rentForSale.addOrder(order);
    }

    public int updateOrder(Order order) {
        return rentForSale.updateOrder(order);
    }

    public List<RentBeforeBuy> queryOrder(Map map) {
        return rentForSale.queryOrder(map);
    }

    public int deleteOrder(int id) {
        return rentForSale.deleteOrder(id);
    }
}
