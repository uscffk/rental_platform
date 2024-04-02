package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 房发科
 * @date 2021/3/6 17:01
 */
@FeignClient(value = "StockService")
public interface IStockService {
    /**
     * 插入库存记录
     * @param stock
     * @return
     */
    @RequestMapping(value = "/stocks",method = RequestMethod.POST)
    CommonResult insertStock(Stock stock);

    /**
     * 查询库存
     * @param id
     * @return
     */
    @RequestMapping(value = "/stocks/{id}",method = RequestMethod.GET)
    CommonResult queryStock(@PathVariable("id") int id);

    /**
     * 增加库存
     * @param commodityId
     * @param count
     * @return
     */
    @RequestMapping(value = "/stocks/increase", method = RequestMethod.POST)
    CommonResult addStock(@RequestParam("commodityId") int commodityId,@RequestParam("count") int count);

    /**
     * 减少库存
     * @param commodityId
     * @param count
     * @return
     */
    @RequestMapping(value = "/stocks/decrease",method = RequestMethod.POST)
    CommonResult decreaseStock(@RequestParam("commodityId") int commodityId,@RequestParam("count") int count);

}
