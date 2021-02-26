package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Stock;
import com.ffk.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 房发科
 * @date 2021/2/23 15:55
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private IStockService stockService;

    /**
     * 根据商品ID查询库存
     * @param commodityId 商品ID
     * @return
     */
    @RequestMapping(value = "/{commodityId}",method = RequestMethod.GET)
    public CommonResult queryStock(@PathVariable int commodityId){
        try {
            Stock stock = stockService.queryStock(commodityId);
            return new CommonResult<Stock>(Constants.SUCCESS_CODE,"查询成功",stock);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult<Stock>(Constants.FAIL_CODE,"查询失败",null);
        }
    }

    /**
     * 减库存
     * @param commodityId 商品ID
     * @param count 数量
     * @return
     */
    @RequestMapping(value = "/decrease/{commodityId}/{count}",method = RequestMethod.PUT)
    public CommonResult decreaseStock(@PathVariable int commodityId,@PathVariable int count){
        if(count<=0){
            return new CommonResult(Constants.FAIL_CODE,"必须大于0",null);
        }
        try {
            int rs = stockService.decreaseStock(commodityId, count);
            if(rs>0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
    }


    public CommonResult addStock(@PathVariable int commodityId,@PathVariable int count){
        if(count<=0){
            return new CommonResult(Constants.FAIL_CODE,"必须大于0",null);
        }
        try {
            int rs = stockService.addStock(commodityId, count);
            if(rs>0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
    }
}
