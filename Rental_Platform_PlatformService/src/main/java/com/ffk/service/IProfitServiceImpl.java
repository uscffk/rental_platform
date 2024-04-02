package com.ffk.service;

import com.ffk.dao.IProfit;
import com.ffk.pojo.Profit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/3 23:46
 */
@Service
public class IProfitServiceImpl implements IProfitService{
    @Autowired
    private IProfit profits;
    @Override
    public int addProfit(Profit profit) {
        return profits.addProfit(profit);
    }

    @Override
    public List<Profit> queryProfit(Map map) {
        return profits.queryProfit(map);
    }
}
