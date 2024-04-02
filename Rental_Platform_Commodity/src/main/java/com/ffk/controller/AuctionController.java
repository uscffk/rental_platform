package com.ffk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ffk.constant.Constants;
import com.ffk.contract.AuctionContract;
import com.ffk.contract.RentOwnershipNFTContract;
import com.ffk.contract.service.AuctionContractService;
import com.ffk.contract.service.RentOwnershipNFTContractService;
import com.ffk.pojo.*;
import com.ffk.service.ICommodityServiceImpl;
import com.ffk.service.IManufacturerService;
import com.ffk.service.IPlatformService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 * ps:写的匆忙 在controller层写了部分业务 糟糕的结构勿效仿 有时间再重构
 */
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    ICommodityServiceImpl commodityService;

    @Autowired
    IManufacturerService manufacturerService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IPlatformService platformService;

    Logger logger = LoggerFactory.getLogger(CommodityController.class);

    /**
     * 商家发起竞拍
     * @param name
     * @param category
     * @param location
     * @param time
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addByPlatform",method = RequestMethod.POST)
    public CommonResult addAuctionByPlatform(@RequestParam("name") String name,
                                             @RequestParam("category") int category,
                                             @RequestParam("detailLoc") String location,
                                             @RequestParam("time") int time
                                             ) throws Exception {
        //租赁权NFT初始化为-1
        int nftToken = -1;
        //加载所有权NFT合约
        RentOwnershipNFTContract rentOwnershipNFTContract = RentOwnershipNFTContractService.load(Constants.RENTOWNERSHIPNFT_CONTRACT_ADDRESS);
        //由于是平台发布的 所以是一块新的区域 铸造一枚NFT
        if(rentOwnershipNFTContract.mintNFT(name,location).send().isStatusOK()){
            //获取刚刚铸造的NFT ID
            nftToken = rentOwnershipNFTContract.tokenCounter().send().intValue()-1;
            //拍卖合约的地址
            String auctionAddress = AuctionContractService.deploy(1000);
            //加载拍卖合约  之所以给拍卖合约打钱是因为在拍卖合约中调用了租赁权NFT合约 代表着合约是交易的发起人  合约账户需要支付交易gas
            AuctionContract auctionContract = AuctionContractService.load(auctionAddress);
            //设置拍卖参数
            auctionContract.setAuctionParameter(
                    BigInteger.valueOf(time),    //持续时间
                    Constants.PLATFORM_COUNT_ADDRESS,  //受益人
                    BigInteger.valueOf(nftToken),   // 租赁权nft ID
                    Constants.RENTOWNERSHIPNFT_CONTRACT_ADDRESS).send();
            //像这种建议后来人去用建造者模式重构一下 写多了比较烦
            Commodity commodity = new Commodity();
            //拍卖合约的地址
            commodity.setAuctionAddress(auctionAddress);
            //类别
            commodity.setCategory(category);
            //详细位置
            commodity.setDetailLoc(location);
            //商品名称
            commodity.setName(name);
            //租赁权NFT
            commodity.setRentOwnershipnft(nftToken);
            //持久进数据库
            commodityService.addCommodity(commodity);

            return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
        }else {
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }

    }

    /**
     *
     * @param price 出价
     * @param manufacturerAddress 商家地址
     * @param auctionAddress 拍卖合约地址
     * @return
     */
    @RequestMapping(value = "/bid",method = RequestMethod.POST)
    public CommonResult bid(@RequestParam("price") int price,
                            @RequestParam("manufacturerAddress") String manufacturerAddress,
                            @RequestParam("auctionAddress") String auctionAddress) throws Exception {

        AuctionContract auctionContract = AuctionContractService.load(auctionAddress);
        if(auctionContract.bid(manufacturerAddress,BigInteger.valueOf(price)).send().isStatusOK()){
            return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
        }else {
            return new CommonResult(Constants.FAIL_CODE,"失败",
                    auctionContract.bid(manufacturerAddress,BigInteger.valueOf(price)).send().getRevertReason());
        }

    }

    /**
     * 结束竞拍
     * @param auctionAddress
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/end",method = RequestMethod.POST)
    @GlobalTransactional
    public CommonResult endAuction(@RequestParam("auctionAddress") String auctionAddress) throws Exception {

        AuctionContract auctionContract = AuctionContractService.load(auctionAddress);
        if(auctionContract.endAuction().send().isStatusOK()){
            //修改相应数据 goods表中的商家归属
            //获得最高出价者
            String highestBidder = (String)AuctionContractService.getAuctionInfo(auctionContract).get("highestBidder");
            //获得最高出价
            Integer highestBid = (Integer)AuctionContractService.getAuctionInfo(auctionContract).get("highestBid");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(System.currentTimeMillis());
            Profit profit = new Profit();
            //链式写法更舒适 建议后面的人可以改改 之前没发现那个注解  其实不用注解也可以实现。。。。
            profit.setAmount(highestBid.intValue())
                    .setFrom(Constants.PROFIT_FROM_Bid)
                    .setProfitTime(time);
            //写进平台收益表
            platformService.addProfit(profit);
            HashMap<Object, Object> conditionMap = new HashMap<>();
            conditionMap.put("contractAddress",highestBidder);

            CommonResult<Manufacturer> commonResult = manufacturerService.queryManufacturer(conditionMap);

            String s = JSON.toJSONString(commonResult.getData(), SerializerFeature.WriteMapNullValue);

            //获得rentNFT
            BigInteger token = (BigInteger)AuctionContractService.getAuctionInfo(auctionContract).get("token");
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("rentOwnershipnft",token.intValue());
            Commodity commodity = commodityService.queryCommodity(objectObjectHashMap).get(0);

            //转对象
            String substring = s.substring(1, s.length() - 1);
            System.out.println(substring);
            Manufacturer manufacturer = JSON.parseObject(substring, Manufacturer.class);
            //获得商家ID
            int id = manufacturer.getId();
            commodity.setManufacturerId(id);
            ArrayList<AuctionInfo> auctionInfos = null;

            //更新redis
            if(redisTemplate.boundValueOps(id+"hadRentownershipNFT").get()!=null){
                CommonResult auctionInfo = this.getAuctionInfo(auctionAddress);
                AuctionInfo data = (AuctionInfo) auctionInfo.getData();
                //先拿到redis中的数据
                List<Deposit> allOwedNFT = (List<Deposit>) redisTemplate.boundValueOps(id+"hadRentownershipNFT").get();
                logger.info("redis中的租赁权NFT数据:{}",allOwedNFT);
                auctionInfos = (ArrayList<AuctionInfo>) JSON.parseArray(JSON.toJSONString(allOwedNFT), AuctionInfo.class);
                //更新商家的租赁权NFT
                data.setCategory(commodity.getCategories().getName());
                data.setName(commodity.getName());
                data.setDetailLoc(commodity.getDetailLoc());
                data.setRentNftToken(commodity.getRentOwnershipnft());
                data.setAuctionAddress(auctionAddress);
                auctionInfos.add(data);
                //加入redis
                redisTemplate.delete(id+"hadRentownershipNFT");
                redisTemplate.boundValueOps(id+"hadRentownershipNFT").set(auctionInfos);
            }
            //持久化
            commodityService.updateCommodity(commodity);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
        }else {
            return new CommonResult(Constants.FAIL_CODE,"失败","竞拍未结束!");
        }
    }

    /**
     * 根据合约地址获取拍卖信息 卖记录  拍卖信息  拍卖状态
     * @param auctionAddress
     * @return
     */
    @RequestMapping(value = "/getAuctionInfoByAddress",method = RequestMethod.POST)
    public CommonResult getAuctionInfo(@RequestParam String auctionAddress) throws Exception {

        try {
            AuctionInfo auctionInfo = new AuctionInfo();
            AuctionContract auctionContract = AuctionContractService.load(auctionAddress);
            Map<String, Object> auctionInfoMap = AuctionContractService.getAuctionInfo(auctionContract);
            Map<String, List> auctionRecordMap = AuctionContractService.getAuctionRecord(auctionContract);
            boolean auctionState = AuctionContractService.getAuctionState(auctionContract);

            auctionInfo.setAuctionInfo(auctionInfoMap);
            auctionInfo.setRecord(auctionRecordMap);
            auctionInfo.setStatus(auctionState);

            return new CommonResult(Constants.SUCCESS_CODE,"成功",auctionInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
            return new CommonResult(Constants.FAIL_CODE,"失败",null);

    }

    /**
     * 查询所有拍卖信息
     * @return
     */
    @RequestMapping(value = "/getAllAuctionInfo",method = RequestMethod.POST)
    public CommonResult queryAllAuctionContract(@RequestParam("offset")  int offset,@RequestParam("limit") int limit) throws Exception {

        HashMap<Object, Object> conditionMap = new HashMap<>();
        conditionMap.put("offset",offset);
        conditionMap.put("limit",limit);

        //查所有
        List<Commodity> commodities = commodityService.queryCommodity(conditionMap);

        ArrayList<AuctionInfo> auctionInfos = new ArrayList<>();
        try {
            //迭代拿合约地址
            for (Commodity commodity : commodities) {
                String auctionAddress = commodity.getAuctionAddress();
                CommonResult auctionInfo = this.getAuctionInfo(auctionAddress);
                AuctionInfo data = (AuctionInfo) auctionInfo.getData();
                data.setCategory(commodity.getCategories().getName());
                data.setName(commodity.getName());
                data.setDetailLoc(commodity.getDetailLoc());
                data.setRentNftToken(commodity.getRentOwnershipnft());
                data.setAuctionAddress(auctionAddress);
                auctionInfos.add(data);
            }
            logger.info("拍卖信息:{}",auctionInfos);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",auctionInfos);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    /**
     * 按商家Id查询已拍得的NFT
     * @param manufacturerId
     * @return
     */
    @RequestMapping(value = "/queryAuctionInfoByMFId",method = RequestMethod.POST)
    public CommonResult queryByManufacturerId(@RequestParam("manufacturerId") int manufacturerId){
        logger.info("按商家ID查租赁权NFT");
        ArrayList<AuctionInfo> auctionInfos = new ArrayList<>();

//        redisTemplate.delete(manufacturerId+"hadRentownershipNFT");

        if(redisTemplate.boundValueOps(manufacturerId+"hadRentownershipNFT").get()==null){
            logger.info("redis中没有数据");
            HashMap<Object, Object> conditionMap = new HashMap<>();
            conditionMap.put("manufacturerId",manufacturerId);
            //查所有
            List<Commodity> commodities = commodityService.queryCommodity(conditionMap);
            try {
                //迭代拿合约地址
                for (Commodity commodity : commodities) {
                    String auctionAddress = commodity.getAuctionAddress();
                    CommonResult auctionInfo = this.getAuctionInfo(auctionAddress);
                    AuctionInfo data = (AuctionInfo) auctionInfo.getData();
                    data.setCategory(commodity.getCategories().getName());
                    data.setName(commodity.getName());
                    data.setDetailLoc(commodity.getDetailLoc());
                    data.setRentNftToken(commodity.getRentOwnershipnft());
                    data.setAuctionAddress(auctionAddress);
                    auctionInfos.add(data);
                }
                //加入redis
                redisTemplate.boundValueOps(manufacturerId+"hadRentownershipNFT").set(auctionInfos);
                return new CommonResult(Constants.SUCCESS_CODE,"成功",auctionInfos);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            //redis中有数据 直接从redis中拿即可
            logger.info("redis中存在该数据");
            List<Deposit> allOwedNFT = (List<Deposit>) redisTemplate.boundValueOps(manufacturerId+"hadRentownershipNFT").get();
            logger.info("redis中的租赁权NFT数据:{}",allOwedNFT);
            auctionInfos = (ArrayList<AuctionInfo>) JSON.parseArray(JSON.toJSONString(allOwedNFT), AuctionInfo.class);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",auctionInfos);
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }
}
