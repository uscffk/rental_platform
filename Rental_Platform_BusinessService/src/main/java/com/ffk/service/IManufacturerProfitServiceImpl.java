package com.ffk.service;

import com.ffk.dao.IManufacturerProfit;
import com.ffk.pojo.ManufacturerProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/4 8:54
 */
@Service
public class IManufacturerProfitServiceImpl implements IManufacturerProfitService{

    @Autowired
    private IManufacturerProfit manufacturerProfits;
    @Override
    public int addManufacturerProfit(ManufacturerProfit manufacturerProfit) {
        return manufacturerProfits.addManufacturerProfit(manufacturerProfit);
    }

    @Override
    public List<ManufacturerProfit> queryManufacturerProfit(Map map) {
        return manufacturerProfits.queryManufacturerProfit(map);
    }
}
