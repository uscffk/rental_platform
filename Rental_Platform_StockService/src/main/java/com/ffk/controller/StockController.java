package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Stock;
import com.ffk.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
     * 插入库存记录
     * @param stock
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult insertStock(@RequestBody Stock stock){
        try {
            int rs = stockService.insertStock(stock);
            if(rs>0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }

    }

    /**
     * 根据商品ID查询库存
     * @param commodityId 商品ID
     * @return
     */
    @RequestMapping(value = "/{commodityId}",method = RequestMethod.GET)
    public CommonResult queryStock(@PathVariable("commodityId") int commodityId){
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
    @RequestMapping(value = "/decrease",method = RequestMethod.POST)
    public CommonResult decreaseStock(@RequestParam("commodityId") int commodityId,@RequestParam("count") int count){
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

    /**
     * 加库存
     * @param commodityId
     * @param count
     * @return
     */
    @RequestMapping(value = "/increase",method = RequestMethod.POST)
    public CommonResult addStock(@RequestParam("commodityId") int commodityId,@RequestParam("count") int count){
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




    @RequestMapping(value = "/updateStock",method = RequestMethod.POST)
    public CommonResult updateStock(@RequestBody Stock stock){
        try {
            int rs = stockService.updateStock(stock);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }
}
