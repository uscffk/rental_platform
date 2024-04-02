package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.UserContractService;
import com.ffk.dao.IUser;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import com.ffk.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author: 房发科
 * @since 2021-02-20
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private IUser user;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICommodityService commodityService;


    @Override
    public int addUser(Users users) {
        return user.addUser(users);
    }

    @Override
    public int deleteUser(int id) {
        return user.deleteUser(id);
    }

    @Override
    public int updateUser(Users users) {
        return user.updateUser(users);
    }

    /**
     * 查询自己的订单
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<Order> queryOrders(Map map) throws Exception {
        //根据用户名查询合约地址
        String username = (String) map.get("username");
        List<Users> users = this.queryUsers(map);
        Users user = users.get(0);
        String contractAddress = user.getContractAddress();
        UserContract userContract = UserContractService.load(contractAddress);
        CommonResult commonResult = orderService.queryOrder(map);
        List<Order> orders =  JSON.parseArray(JSON.toJSONString(commonResult.getData()), Order.class);
        //遍历订单动态拼接
        for (Order order : orders) {
            //获取订单id
            int orderId = order.getOrderId();
            //根据订单id查询商品id
            int goodIdByOrderId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
            //根据商品id查询商品
            System.out.println("订单编号:"+goodIdByOrderId);
//            HashMap<Object, Object> commodityHashMap = new HashMap<>(1);
//            commodityHashMap.put("id",goodIdByOrderId);
            CommonResult comResult = commodityService.queryCommodityById(goodIdByOrderId);
            Commodity commodity = JSON.parseObject(JSON.toJSONString(comResult.getData()), Commodity.class);
            //拼接返回结果
            System.out.println(commodity);
            order.setCommodity(commodity);
            System.out.println(order);
        }
        return orders;
    }

    /**
     * 余额查询
     * @param username
     * @return
     */
    @Override
    public BigInteger queryBalance(String username) throws Exception {
        //根据用户名查询用户信息
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(1);
        objectObjectHashMap.put("username",username);
        List<Users> users = this.queryUsers(objectObjectHashMap);
        Users user = users.get(0);
        //获取用户合约地址
        String userContractAddress = user.getContractAddress();
        //调用用户合约服务
        UserContract userContract = UserContractService.load(userContractAddress);
        BigInteger balance = UserContractService.getBalance(userContract);
        System.out.println("余额为:"+balance);
        return balance;
    }

    @Override
    public Users queryById(int id) throws Exception {
        Users users = user.queryById(id);
        String contractAddress = users.getContractAddress();
        UserContract userContract = UserContractService.load(contractAddress);
        int credit = UserContractService.getCredit(userContract);
        int balance = UserContractService.getBalance(userContract).intValue();
        users.setBalance(balance);
        users.setCredit(credit);
        return users;
    }

    @Override
    public int queryTotal() {
        return user.queryTotal();
    }

    @Override
    public Users queryByAddress(String address) {
        return user.queryByAddress(address);
    }

    public List<Users> queryUser(Map map){
        return  user.queryUsers(map);
    }

    @Override
    public List<Users> queryUsers(Map map) throws Exception {
        List<Users> users = this.queryUser(map);
        for (Users userCre : users) {
            System.out.println(userCre);
            String userCreContractAddress = userCre.getContractAddress();
            //加载用户合约
            System.out.println(userCreContractAddress);
            UserContract userContract = UserContractService.load(userCreContractAddress);
            int credit = UserContractService.getCredit(userContract);
            int balance = UserContractService.getBalance(userContract).intValue();
            userCre.setBalance(balance);
            userCre.setCredit(credit);
        }
        return users;
    }

    @Override
    public Users login(String username) throws Exception {

        Users users = user.login(username);
        String usersContractAddress = users.getContractAddress();
        //查询信用积分
        UserContract userContract = UserContractService.load(usersContractAddress);
        int credit = UserContractService.getCredit(userContract);
        users.setCredit(credit);
        return users;

    }



}
