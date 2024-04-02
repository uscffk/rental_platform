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
 * @create 2022/3/3
 */
@FeignClient("CommodityService")
public interface ICommodityService {

    /**
     * 按ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/commodities/queryById",method = RequestMethod.GET)
    CommonResult queryById(@RequestParam("id") int id);

    /**
     * 根据Id查NFT
     * @param nftId
     * @return
     */
    @RequestMapping(value = "/nftCommodity/queryById",method = RequestMethod.POST)
    CommonResult queryNFTByNftId(@RequestParam(value = "nftId") int nftId);
}