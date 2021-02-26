package com.ffk.service;

import com.ffk.dao.ISharingRent;
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
public class ISharingRentServiceImpl implements IOrderService{
    @Autowired
    private ISharingRent order;

    public int addOrder(Order order) {
        return 0;
    }

    public int updateOrder(Order order) {
        return 0;
    }

    public List<RentBeforeBuy> queryOrder(Map map) {
        return null;
    }

    public int deleteOrder(int id) {
        return 0;
    }
}
