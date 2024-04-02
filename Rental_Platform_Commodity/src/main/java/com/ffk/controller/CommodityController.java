package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.service.ICommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/18 15:53
 */
@RestController
@RequestMapping("/commodities")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;

    Logger logger = LoggerFactory.getLogger(CommodityController.class);

    /**
     * 添加商品
     * @param commodity
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult addCommodity(@RequestBody Commodity commodity) throws Exception {
        int rs = commodityService.addCommodity(commodity);
        //添加成功
        if(rs > 0){
            return new CommonResult(Constants.SUCCESS_CODE,"添加商品成功",rs);
        }
        //添加失败
        return new CommonResult(Constants.FAIL_CODE,"添加商品失败",null);
    }

    /**
     * 更新商品
     * @param commodity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult updateCommodity(@RequestBody Commodity commodity){

        int rs = commodityService.updateCommodity(commodity);
        if(rs > 0){
            return new CommonResult(Constants.SUCCESS_CODE,"更新商品成功",null);
        }
        return new CommonResult(Constants.FAIL_CODE,"更新商品失败",null);
    }

    /**
     * 查询商品
     * @param conditionMap
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public CommonResult queryCommodity(@RequestBody Map conditionMap) {
        logger.info("查询条件:{}",conditionMap);
        if(conditionMap.size()>=0){
            try {
                logger.info("查询条件:{}",conditionMap);
                List<Commodity> commodities = commodityService.queryCommodity(conditionMap);
                logger.info("rsult:{}",commodities);
                return new CommonResult(Constants.SUCCESS_CODE,"查询成功",commodities);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"查询失败",null);
    }

    /**
     * 按ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryById",method = RequestMethod.GET)
    public CommonResult queryById(@RequestParam("id") int id){
        try {
            logger.info("查询的商品Id:{}",id);
            Commodity commodity = commodityService.queryById(id);
            logger.info("查询商品结果:{}",commodity);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",commodity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }


    @RequestMapping(value = "/queryTotal",method = RequestMethod.POST)
    public CommonResult queryTotalAmount(){
        try{
            int total = commodityService.getTotal();
            logger.info("数量:{}",total);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",total);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

}
