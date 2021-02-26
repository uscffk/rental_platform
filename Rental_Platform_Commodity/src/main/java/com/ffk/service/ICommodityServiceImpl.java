package com.ffk.service;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.ffk.dao.ICommodity;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Stock;
import jdk.internal.org.objectweb.asm.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/18 10:12
 */
@Service
@Slf4j
public class ICommodityServiceImpl implements ICommodityService{

    @Autowired
    private ICommodity commodities;
    @Autowired
    private IManufacturerService manufacturerService;
    @Autowired
    private IStockService stockService;


    @Override
    public int addCommodity(Commodity commodity) {
        return commodities.addCommodity(commodity);
    }

    @Override
    public int deleteCommodity(int id) {
        return commodities.deleteCommodity(id);
    }

    @Override
    public int updateCommodity(Commodity commodity) {
        return commodities.updateCommodity(commodity);
    }

    @Override
    public List<Commodity> queryCommodity(Map map) {
        //在此处组装返回结果
        List<Commodity> commodities = this.commodities.queryCommodity(map);
        for (Commodity commodity : commodities) {
            int id = commodity.getId();
            CommonResult<Stock> commonResult = stockService.queryStock(id);
            String s = JSON.toJSONString(commonResult.getData());
            Stock stock = JSON.parseObject(s, Stock.class);
            try {
                commodity.setStock(stock);
            }
            catch (Exception  e){
                e.printStackTrace();
            }
        }
        return commodities;
    }
}
