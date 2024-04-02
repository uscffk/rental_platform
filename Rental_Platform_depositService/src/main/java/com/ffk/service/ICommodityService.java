package com.ffk.service;

import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/10 21:23
 */
@FeignClient("CommodityService")
public interface ICommodityService {
    /**
     * 上架商品
     * @param commodities
     * @return
     */
    @RequestMapping(value = "/commodities/add",method = RequestMethod.POST)
    CommonResult addCommodity(Commodity commodities);

    /**
     * 修改商品
     * @param commodities
     * @return
     */
    @RequestMapping(value = "/commodities/update",method = RequestMethod.POST)
    CommonResult updateCommodity(Commodity commodities);


    /**
     * 查询商品
     * @param conditionMap
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    CommonResult queryCommodity(Map conditionMap);

    /**
     * 根据Id查NFT
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/nftCommodity/queryById",method = RequestMethod.POST)
    CommonResult queryNFTByNftId(@RequestParam(value = "nftId") int nftId);
}
