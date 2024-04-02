package com.ffk.service;

import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 房发科
 * @date 2021/2/25 22:02
 */

/**
 * 远程调用库存服务
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
     * 减少库存  [这是一个遗留问题 我一开始是全部想用restful风格的api的 后来想想还是算了吧 前后端联调太麻烦 post yyds]
     * @param commodityId
     * @param count
     * @return
     */
    @RequestMapping(value = "/decrease/{commodityId}/{count}",method = RequestMethod.PUT)
    CommonResult decreaseStock(@PathVariable("commodityId") int commodityId,@PathVariable("count") int count);


    /**
     * 更新库存
     * @param stock
     * @return
     */
    @RequestMapping(value = "/stocks/updateStock",method = RequestMethod.POST)
    CommonResult updateStock(@RequestBody Stock stock);

}
