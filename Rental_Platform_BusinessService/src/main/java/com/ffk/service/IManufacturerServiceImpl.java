package com.ffk.service;

import com.ffk.constant.Constants;
import com.ffk.contract.ManufacturerContract;
import com.ffk.contract.service.ManufacturerContractService;
import com.ffk.dao.IManufacturer;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Manufacturer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/3 15:21
 */
@Service
public class IManufacturerServiceImpl implements IManufacturerService{

    @Autowired
    private IManufacturer manufacturers;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPlatformService platformService;


    /**
     * 查询厂商
     * @param map
     * @return
     */
    @Override
    public List<Manufacturer> queryManufacturer(Map map) throws Exception {
        List<Manufacturer> manufacturers = this.manufacturers.queryManufacturer(map);
        //这里别查余额了  合链交互太频繁 很慢 暂且注释掉 不查这个小程序首页能快2s
//        for (Manufacturer manufacturer : manufacturers) {
//            String contractAddress = manufacturer.getContractAddress();
//            ManufacturerContract manufacturerContract = ManufacturerContractService.load(contractAddress);
//            //查余额
//            int balance = ManufacturerContractService.getBalance(manufacturerContract).intValue();
//            manufacturer.setBalance(balance);
//        }
        return manufacturers;
    }

    @Override
    public Manufacturer queryManufacturerById(int manufacturerId) throws Exception {
        Manufacturer manufacturer = manufacturers.queryManufacturerById(manufacturerId);
        //同上
//        String contractAddress = manufacturer.getContractAddress();
//        ManufacturerContract manufacturerContract = ManufacturerContractService.load(contractAddress);
//        //查余额
//        int balance = ManufacturerContractService.getBalance(manufacturerContract).intValue();
//        manufacturer.setBalance(balance);
        return manufacturer;
    }

    /**
     * 增加厂商
     * @param manufacturer
     * @return
     */
    @Override
    public int addManufacturer(Manufacturer manufacturer) throws Exception {
        String contractAddress = ManufacturerContractService.deploy();
        manufacturer.setContractAddress(contractAddress);
        return manufacturers.addManufacturer(manufacturer);
    }

    /**
     * 根据商家Id查询余额
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int queryBalance(int id) throws Exception {
        Manufacturer manufacturer = manufacturers.queryManufacturerById(id);
        String manufacturerContractAddress = manufacturer.getContractAddress();
        //直接来了
        ManufacturerContract manufacturerContract = ManufacturerContractService.load(manufacturerContractAddress);
        return ManufacturerContractService.getBalance(manufacturerContract).intValue();

    }

    /**
     * 查询反馈
     * @return
     */
    @Override
    public CommonResult queryFeedBack() {
        return userService.queryFeedback();
    }

    /**
     * 更新厂商
     * @param manufacturer
     * @return
     */
    @Override
    public int updateManufacturer(Manufacturer manufacturer) {
        return manufacturers.updateManufacturer(manufacturer);
    }

    /**
     * 按条件统计收益
     * @param map
     * @return
     */
    @Override
    public CommonResult queryProfit(Map map) {
        //写统计收益的逻辑
        //return manufacturers.;
        return new CommonResult(Constants.SUCCESS_CODE,"ok");
    }

    @Override
    public int getTotal() {
        return manufacturers.getTotal();
    }

    /**
     * 充值
     * @param username
     * @param amount
     */
    @Override
    public CommonResult recharge(String username, float amount) {
         return  platformService.recharge(username,amount);
    }

    /**
     * 查询自家商品
     * @param map
     * @return
     */
    @Override
    public CommonResult queryCommodity(Map map) {
        return commodityService.queryCommodity(map);
    }

    /**
     * 上架商品
     * @param commodity
     * @return
     */
    @Override
    public CommonResult addCommodity(Commodity commodity) {
        return commodityService.addCommodity(commodity);
    }

    /**
     * 更新商品
     * @param commodity
     * @return
     */
    @Override
    public CommonResult updateCommodity(Commodity commodity) {
        return commodityService.updateCommodity(commodity);
    }

    /**
     * 查询用户
     * @param map
     * @return
     */
    @Override
    public CommonResult queryUsers(Map map) {
        return userService.queryUsers(map);
    }

    /**
     * 提现
     * @param username
     * @param amount
     * @return
     */
    @Override
    public CommonResult getRMB(String username, float amount) {
        return platformService.getRMB(username,amount);
    }

    /**
     * 登录
     * @param username
     * @return
     */
    @Override
    public Manufacturer queryLogin(String username) {
        return manufacturers.queryLogin(username);
    }

    /**
     * 删除厂商
     * @param id
     * @return
     */
    @Override
    public int deleteManufacturer(int id) {
        return manufacturers.deleteManufacturer(id);
    }
}
