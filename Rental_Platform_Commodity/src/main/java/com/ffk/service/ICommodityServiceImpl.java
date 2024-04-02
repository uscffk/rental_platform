package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.dao.ICommodity;
import com.ffk.pojo.*;
import com.ffk.utils.RedisUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import java.util.*;

/**
 * @author 房发科
 * @date 2021/2/18 10:12
 */
@Service
@Slf4j
public class ICommodityServiceImpl implements ICommodityService {

    @Autowired
    private ICommodity commodities;
    @Autowired
    private IManufacturerService manufacturerService;
    @Autowired
    private IStockService stockService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Web3j web3j;
    @Autowired
    private IPlatformService platformService;
    @Autowired
    private INFTCommodityService nftCommodityService;
    @Autowired
    private IBackLocationService backLocationService;

    Logger logger = LoggerFactory.getLogger(INFTCommodityServiceImpl.class);

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int addCommodity(Commodity commodity) throws Exception {
        //1.添加到商品表
        //2.添加到库存表
        commodities.addCommodity(commodity);
        //添加库存
        HashMap<Object, Object> map = new HashMap<>(1);
        map.put("name", commodity.getName());
        List<Commodity> commoditySet = commodities.queryCommodity(map);
        Collections.sort(commoditySet);
        //得到刚刚添加进去的商品id   (id可以尝试生成唯一性id的方法 例如UUID，这里后续将完善)
        int id = commoditySet.get(commoditySet.size() - 1).getId();
        Stock stock = new Stock();
        stock.setCommodityId(id);
        stock.setNumber(0);
        //插入库存表
        stockService.insertStock(stock);
        //将位置信息持久化进redis
        return 1;
    }

    /**
     * 下架商品（暂不实现，也没啥必要啊）
     * @param id
     * @return
     */
    @Override
    public int deleteCommodity(int id) {
        return commodities.deleteCommodity(id);
    }

    /**
     * 更新商品信息
     * @param commodity
     * @return
     */
    @Override
    public int updateCommodity(Commodity commodity) {
        return commodities.updateCommodity(commodity);
    }

    /**
     * 商品查询
     * @param map 传入map实现多条件查询
     * @return
     */
    @Override
    @HystrixCommand
    public List<Commodity> queryCommodity(Map map) {
        if(map.get("offset")!=null){
            map.put("startIndex",((Integer.parseInt(map.get("offset").toString())-1)*Integer.parseInt(map.get("limit").toString())));
        }
        //在此处组装返回结果 命名真难。。。
        List<Commodity> commodities = this.commodities.queryCommodity(map);
        for (Commodity commodity : commodities) {
            //商品Id
            int id = commodity.getId();
            //商家Id
            int manufacturerId = commodity.getManufacturerId();
            //种类类别 这里我在sql里面做了映射 感觉没多大必要（当然实际生产情况通过sql映射可以减少和数据库的交互） 主要是项目是分布式 分布式连表查询只能拼接或者用中间件工具去做
            CommodityCategory categories = commodity.getCategories();
            //这写法我都服了我自己
            commodity.setCategory(categories.getId());
            //查询库存
            CommonResult<Stock> commonResultStock = stockService.queryStock(id);
            String s = JSON.toJSONString(commonResultStock.getData());
            //转成stock对象
            Stock stock = JSON.parseObject(s, Stock.class);
            //查询厂商
            CommonResult commonResultManu = manufacturerService.queryManufacturerById(manufacturerId);
            Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(commonResultManu.getData()), Manufacturer.class);
            //查商品
            List<NFTCommodity> nftCommodities = nftCommodityService.queryByCommodityId(id);
            //查询站点
            List<BackLocation> backLocations = backLocationService.queryById(id);
//          HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//          objectObjectHashMap.put("goodsId",id);
//          List<NFTCommodity> nftCommodities = nftCommodityService.queryNFTCommodity(objectObjectHashMap);
            try {
                commodity.setStock(stock);
                commodity.setManufacturers(manufacturer);
                commodity.setNftCommodityList(nftCommodities);
                commodity.setBackLocations(backLocations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(commodities.toString());
        return commodities;
    }

    /**
     * 按Id查询商品 拼接字符串
     * @param id
     * @return
     */
    @Override
    public Commodity queryById(int id) {
        logger.info("按Id:{}",id);
        Commodity commodity = commodities.queryById(id);
        logger.info("商品:{}",commodity);
        commodity.setCategory(commodity.getCategories().getId());
        //商家Id
        int manufacturerId = commodity.getManufacturerId();
        //查询库存
        CommonResult<Stock> commonResultStock = stockService.queryStock(id);
        String s = JSON.toJSONString(commonResultStock.getData());
        //转成stock对象
        Stock stock = JSON.parseObject(s, Stock.class);
        //查询厂商
        CommonResult commonResultManu = manufacturerService.queryManufacturerById(manufacturerId);
        Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(commonResultManu.getData()), Manufacturer.class);
        logger.info("商家:{}",manufacturer);
        //查商品  写这个会栈溢出 循环调用
        //List<NFTCommodity> nftCommodities = nftCommodityService.queryByCommodityId(id);
        //查询站点
        List<BackLocation> backLocations = backLocationService.queryById(id);
        try {
            commodity.setStock(stock);
            commodity.setManufacturers(manufacturer);
            //commodity.setNftCommodityList(nftCommodities);
            commodity.setBackLocations(backLocations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commodity;
    }

    @Override
    public int getTotal() {
        return commodities.getTotal();
    }

    @Override
    public List<Integer> queryCommodityIdByMId(int mid) {

        return commodities.queryCommodityIdByMId(mid);
    }

    @Override
    public List<String> getAllAuctionAddress() {
        return commodities.getAllAuctionAddress();
    }
}
