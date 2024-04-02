package com.ffk.service;

import com.ffk.constant.Constants;
import com.ffk.contract.ManufacturerContract;
import com.ffk.contract.service.ManufacturerContractService;
import com.ffk.dao.INFTCommodity;
import com.ffk.pojo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Service
public class INFTCommodityServiceImpl implements INFTCommodityService{

    @Autowired
    private INFTCommodity nftCommodityDao;

    @Autowired
    private IStockService stockService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IPlatformService platformService;

    @Autowired
    private ICommentService commentService;

    Logger logger = LoggerFactory.getLogger(INFTCommodityServiceImpl.class);

    /**
     * 上架商品 支付佣金 这是一个全局事务（分布式事务） 用GlobalTransactional注解
     * @param nftCommodity
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int addNFTCommodity(NFTCommodity nftCommodity) throws Exception {
        //持久化进NFT表
        nftCommodityDao.addNFTCommodity(nftCommodity);
        //加库存
        stockService.addStock(nftCommodity.getGoodsId(),1);
        //商品id
        int goodsId = nftCommodity.getGoodsId();
        //查询商家
        Commodity commodity = commodityService.queryById(goodsId);
        //得到商家
        Manufacturer manufacturers = commodity.getManufacturers();
        //商家合约地址
        String manufacturerAddress = manufacturers.getContractAddress();
        //加载商家合约
        ManufacturerContract manufacturerContract = ManufacturerContractService.load(manufacturerAddress);
        //得到单期租金
        int rentPrice = commodity.getRentPrice();
        //获取佣金率
        double commissionRate = Constants.COMMISSION_RATE;
        //计算上架单件租赁商品的佣金
        BigInteger commission = BigInteger.valueOf(new Double(commissionRate * rentPrice).intValue());
        //商家合约给平台合约打钱
        ManufacturerContractService.addCommodity(Constants.PLATFORM_CONTRACT_ADDRESS,commission,manufacturerContract);
        //判断佣金是否为0
        Profit profit = new Profit();
        //平台的收益来源于商家上架商品
        profit.setFrom(Constants.PROFIT_FROM_MANUFACTURER);
        profit.setAmount(commission.intValue());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        profit.setProfitTime(time);
        //插入收益表
        platformService.addProfit(profit);
        return 1;
    }

    @Override
    public int updateNFTCommodity(NFTCommodity nftCommodity) {
        return nftCommodityDao.updateNFTCommodity(nftCommodity);
    }

    @Override
    public List<NFTCommodity> queryByCommodityId(int id) {
        List<NFTCommodity> nftCommodities = nftCommodityDao.queryByCommodityId(id);
        for (NFTCommodity nftCommodity : nftCommodities) {
            int nftTokenId = nftCommodity.getNftTokenId();
            int goodsId = nftCommodity.getGoodsId();
            Commodity commodity = commodityService.queryById(goodsId);
            nftCommodity.setCommodity(commodity);
            //查评论 这里别查评论了 太慢
            //List<Comment> comments = commentService.queryByNFTId(nftTokenId);
            //设置评论
            //nftCommodity.setComments(comments);
        }
        logger.info("queryByCommodityId:{}",nftCommodities);
        return nftCommodities;
    }

    @Override
    public List<NFTCommodity> queryNFTCommodity(Map map) {

        List<NFTCommodity> nftCommodities = nftCommodityDao.queryNFTCommodity(map);
        for (NFTCommodity nftCommodity : nftCommodities) {
            int nftTokenId = nftCommodity.getNftTokenId();
            int goodsId = nftCommodity.getGoodsId();
            Commodity commodity = commodityService.queryById(goodsId);
            nftCommodity.setCommodity(commodity);
        }
        logger.info("queryNFTCommodity:{}",nftCommodities);
        return nftCommodities;
    }

    @Override
    public NFTCommodity queryByNftId(int nftId) {
        logger.info("nftId:{}",nftId);
        NFTCommodity nftCommodity = nftCommodityDao.queryByNftId(nftId);
        logger.info("查询的NFTByNFTID:{}",nftCommodity);
        int nftTokenId = nftCommodity.getNftTokenId();
        int goodsId = nftCommodity.getGoodsId();
        Commodity commodity = commodityService.queryById(goodsId);
        nftCommodity.setCommodity(commodity);
        //查评论
        List<Comment> comments = commentService.queryByNFTId(nftTokenId);
        logger.info("查到的评论:{}",comments);
        //设置评论
        nftCommodity.setComments(comments);
        return nftCommodity;
    }
}
