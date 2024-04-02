package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.NFTCommodity;
import com.ffk.service.ICommodityService;
import com.ffk.service.INFTCommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/11
 */
@RestController
@RequestMapping("/nftCommodity")
public class NFTCommodityController {


    @Autowired
    INFTCommodityService nftCommodityService;

    @Autowired
    ICommodityService commodityService;

    Logger logger = LoggerFactory.getLogger(NFTCommodityController.class);
    /**
     * 多条件查询
     * @param conditionMap 查询条件
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public CommonResult queryNFTCommodity(@RequestBody Map conditionMap){
        try {
            List<NFTCommodity> nftCommodities = nftCommodityService.queryNFTCommodity(conditionMap);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",nftCommodities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    @RequestMapping(value = "/queryById",method = RequestMethod.POST)
    public CommonResult queryNFTByNftId(@RequestParam(value = "nftId") int nftId){
        try {
            logger.info("查询的NFTId:{}",nftId);
            NFTCommodity nftCommodity = nftCommodityService.queryByNftId(nftId);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",nftCommodity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    /**
     * 更新 主要是归还商品时  更新位置及状态信息
     * @param nftCommodity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult updateNFTCommodity(@RequestBody NFTCommodity nftCommodity){
        try{
            int rs = nftCommodityService.updateNFTCommodity(nftCommodity);
            if(rs != 0){
                return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    /**
     * 上架商品
     * @param amount
     * @param longitude
     * @param latitude
     * @param detailLoc
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult addNFTCommodity(@RequestParam("amount") int amount,
                                        @RequestParam("longitude") String longitude,
                                        @RequestParam("latitude") String latitude,
                                        @RequestParam("detailLoc") String detailLoc,
                                        @RequestParam("goodsId") int goodsId
                                        ) throws Exception {
        //根据goodsId获取商家Id
        Commodity commodities = commodityService.queryById(goodsId);
        //商家合约地址
        String manufacturerContractAddress = commodities.getManufacturers().getContractAddress();

        try {
            //这里可能先不能生成NFT ID 最后再来生成 因为这样可能会导致链上链下数据的不一致  先放着
            //先生成对应的NFTId
            for (int i = 0; i < amount; i++) {
                CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
                //铸造商品的NFT
                if(commodityNFTContract.mintNFT(manufacturerContractAddress).send().isStatusOK()){
                    NFTCommodity nftCommodity = new NFTCommodity();
                    //NFT ID
                    int NftTokenId = commodityNFTContract.tokenCounter().send().intValue()-1;
                    nftCommodity.setDetailLoc(detailLoc);
                    nftCommodity.setLatitude(latitude);
                    nftCommodity.setLongitude(longitude);
                    nftCommodity.setGoodsId(goodsId);
                    nftCommodity.setNftTokenId(NftTokenId);
                    //持久化
                    nftCommodityService.addNFTCommodity(nftCommodity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }
        return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
    }
}

