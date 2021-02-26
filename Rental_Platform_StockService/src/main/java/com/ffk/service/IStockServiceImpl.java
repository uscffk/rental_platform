package com.ffk.service;

import com.ffk.dao.IStock;
import com.ffk.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 房发科
 * @date 2021/2/23 15:51
 */
@Service
public class IStockServiceImpl implements IStockService{

    @Autowired
    private IStock stockService;

    @Override
    public int insertStock(Stock stock) {
        return stockService.insertStock(stock);
    }

    @Override
    public int deleteStock(int commodityId) {
        return stockService.deleteStock(commodityId);
    }



    @Override
    public int decreaseStock(int commodityId, int count) {
        return stockService.decreaseStock(commodityId,count);
    }

    @Override
    public int addStock(int commodityId, int count) {
        return stockService.addStock(commodityId,count);
    }

    @Override
    public Stock queryStock(int commodityId) {
        return stockService.queryStock(commodityId);
    }
}
