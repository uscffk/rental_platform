package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.UserContractService;
import com.ffk.dao.IOrder;
import com.ffk.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/20 19:47
 */
@Service
public class OrderServiceImpl implements IOrderService {

    Logger logger = LoggerFactory.getLogger(IOrderService.class);

    @Autowired
    private IOrder  order;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommodityService commodityService;

    @Override
    public int updateOrder(Order orders) {
        return order.updateOrder(orders);
    }

    @Override
    public int queryNFTIDByOrderId(int orderId) throws Exception {
        CommonResult commonResult = userService.queryUser(new HashMap());
        List<Users> users = JSON.parseArray(JSON.toJSONString(commonResult.getData()), Users.class);
        //遍历用户
        for (Users user : users) {
            //得到地址
            String contractAddress = user.getContractAddress();
            UserContract userContract = UserContractService.load(contractAddress);
            int nftId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
            if(nftId!=-1)
                return  nftId;
        }
        return 0;
    }

    @Override
    public List<Order> queryOrder(Map map) throws Exception {

        //先从数据库查询所有订单
        List<Order> orders = order.queryOrder(map);
        logger.info("订单"+orders);
        //如果是查用户的
        if(map.containsKey("userId")){
            logger.info("用户:"+map.get("userId").toString());
            //调用用户服务
            CommonResult commonResult = userService.queryById((Integer) map.get("userId"));
            //转成用户对象
            Users users = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Users.class);
            //得到地址
            String contractAddress = users.getContractAddress();
            //加载合约
            UserContract userContract = UserContractService.load(contractAddress);
            //得到用户的所有订单id
            List<BigInteger> allOrdersId = UserContractService.getAllOrdersId(userContract);

            ArrayList<BigInteger> objects = new ArrayList<>();

            for (BigInteger integer : allOrdersId) {
                objects.add(integer);
            }
            logger.info("用户的所有订单id:"+String.valueOf(objects));

            List<Order> rs = new ArrayList<>();
            //遍历订单
            for (Order order : orders) {
                if(objects.contains(BigInteger.valueOf(order.getOrderId()))){
                    //添加
                    //拼接商品
                    int nftId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(order.getOrderId()), userContract);
                    logger.info("nftId:{}",nftId);
                    //调用商品服务
                    CommonResult nftResult = commodityService.queryNFTByNftId(nftId);
                    logger.info("nftResult:{}",nftResult);
                    //转成商品对象
                    NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);
                    //设置商品
                    order.setCommodity(nftCommodity.getCommodity());
                    rs.add(order);
                }
            }
            return rs;
        }
        return orders;
    }
    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Override
    public Order queryById(int id) {

        return order.queryById(id);
    }

    /**
     * 根据NFT ID找订单
     * @param nftID
     * @return
     */
    @Override
    public Order queryOrderByNFTID(int nftID) throws Exception {

        HashMap<Object, Object> conditionMap = new HashMap<>();
        CommonResult usersResult = userService.queryUser(conditionMap);
        List<Users> users = JSON.parseArray(JSON.toJSONString(usersResult.getData()), Users.class);
        int ordersId = 0;
        //遍历用户
        for (Users user : users) {
            //拿到用户地址
            String contractAddress = user.getContractAddress();
            //加载用户合约
            UserContract userContract = UserContractService.load(contractAddress);
            //拿到订单-nft映射
            List<BigInteger> allOrdersId = UserContractService.getAllOrdersId(userContract);
            logger.info("所有订单id:{}",allOrdersId);
            for (int i = allOrdersId.size()-1; i >= 0; i--) {
                BigInteger orderId = allOrdersId.get(i);
                int nftIdByOrderId = UserContractService.getGoodIdByOrderId(orderId, userContract);
                if(nftID == nftIdByOrderId){
                    ordersId = orderId.intValue();
                    //调用订单服务
                    Order order = this.order.queryById(ordersId);
                    return order;
                }
            }
        }

        return null;
    }

    /**
     *
     * @param orders
     * @return 返回新插入订单的id
     */
    @Override
    public int addOrder(Order orders) throws Exception {
        logger.info("要添加的订单:"+orders.toString());
        //持久化
        order.addOrder(orders);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(1);
        objectObjectHashMap.put("creatTime",orders.getCreatTime());
        List<Order> orderList = this.queryOrder(objectObjectHashMap);
        Order getOrder = orderList.get(0);
        return getOrder.getOrderId();
    }
}
