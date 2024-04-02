package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.contract.DepositContract;
import com.ffk.contract.UserContract;
import com.ffk.contract.service.DepositContractService;
import com.ffk.contract.service.UserContractService;
import com.ffk.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/17 21:50
 */
@Service
public class DepositServiceImpl implements DepositService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService  orderService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(DepositServiceImpl.class);

    @Override
    public boolean mintDepositNFT(String userAddress, int commodityNFTToken, int amount) throws Exception {
        ArrayList<Deposit> depositArrayList;
        DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
        UserContract userContract = UserContractService.load(userAddress);
        //先给平台转钱 而后平台铸造押金NFT
        UserContractService.transfer(Constants.PLATFORM_CONTRACT_ADDRESS, BigInteger.valueOf(amount), userContract); // 转押金)
        //平台铸造押金NFT 所有权在用户手上
        DepositContractService.mintDepositNFT(userAddress,BigInteger.valueOf(commodityNFTToken),BigInteger.valueOf(amount),depositContract);
        //更新redis
        if(redisTemplate.boundValueOps("allDeposit").get()!=null){
            logger.info("更新redis");
            //先拿到redis中的数据
            List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps("allDeposit").get();
            logger.info("redis中的押金数据:{}",allDeposit);
            depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
            logger.info("更新redis前条数:{}",depositArrayList.size());
            Deposit deposit = new Deposit();
            deposit.setCommodityNFT(commodityNFTToken);
            deposit.setNftId(depositContract.tokenCounter().send().intValue()-1);
            deposit.setEffective(true);
            deposit.setAmount(amount);
            CommonResult commonResult = commodityService.queryNFTByNftId(commodityNFTToken);
            NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(commonResult.getData()), NFTCommodity.class);
            deposit.setNftCommodity(nftCommodity);
            depositArrayList.add(deposit);
            //更新回redis
            redisTemplate.delete("allDeposit");
            redisTemplate.boundValueOps("allDeposit").set(depositArrayList);
            logger.info("更新redis后条数:{}",depositArrayList.size());
        }

        //根据地址查用户id 我煞笔了
        CommonResult commonResult = userService.queryByContractAddress(userAddress);
        Users users = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Users.class);
        //获得Id
        int userId = users.getId();
        if(redisTemplate.boundValueOps(userId+"allDeposit").get()!=null){
            //redis中存在数据 更新
            logger.info("更新用户在redis中的押金数据");
            //先拿到redis中的数据
            List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps(userId+"allDeposit").get();
            depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
            Deposit deposit = new Deposit();
            deposit.setCommodityNFT(commodityNFTToken);
            deposit.setNftId(depositContract.tokenCounter().send().intValue()-1);
            deposit.setEffective(true);
            deposit.setAmount(amount);
            CommonResult commonResults = commodityService.queryNFTByNftId(commodityNFTToken);
            NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(commonResults.getData()), NFTCommodity.class);
            deposit.setNftCommodity(nftCommodity);
            depositArrayList.add(deposit);
            //更新回redis
            redisTemplate.delete(userId+"allDeposit");
            redisTemplate.boundValueOps(userId+"allDeposit").set(depositArrayList);
        }

        return true;
    }

    @Override
    public boolean backDeposit(int orderId) throws Exception {

        logger.info("退押金的订单:{}",orderId);
        //调用订单服务
        CommonResult orderResult = orderService.queryById(orderId);
        //转成order类型
        Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
        //订单的押金
        int depositAmount = order.getDeposit();
        //该笔订单并未缴纳押金
        if(depositAmount==0){
            return true;
        }
        int userId = -1;
        //用户合约地址(该把押金退给谁)
        String userContractAddress =  "";
        HashMap<Object, Object> userCondition = new HashMap<>();
        CommonResult userList = userService.queryUsers(userCondition);
        List<Users> users = JSON.parseArray(JSON.toJSONString(userList.getData()), Users.class);
        for (Users user : users) {
            //拿到用户的合约地址
            String contractAddress = user.getContractAddress();
            UserContract userContract = UserContractService.load(contractAddress);
            //查询
            List allOrdersId = UserContractService.getAllOrdersId(userContract);
            logger.info("用户的订单id:{}",allOrdersId);
            logger.info("用户:{}",user);
            if(allOrdersId.contains(BigInteger.valueOf(orderId))){
                //是该用户的订单
                userContractAddress = user.getContractAddress();
                logger.info("用户:{}",user);
                logger.info("订单属于:{}",userContractAddress);
                userId = user.getId();
                break;
            }
        }
        logger.info("订单属于:{}",userContractAddress);
        //加载押金合约
        DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
        //加载用户合约
        UserContract userContract = UserContractService.load(userContractAddress);
        int NFTId = UserContractService.getGoodIdByOrderId(BigInteger.valueOf(orderId), userContract);
        //根据用户ID拿到用户所有的押金NFTID
        List<Deposit> deposits = this.queryByUserId(userId);
        //要退的押金NFTID
        int depositNFTID = -1;
        Deposit depositUpdate = null;
        //遍历
        for (Deposit deposit : deposits) {
            //押金的有效性
            boolean effective = deposit.isEffective();
            NFTCommodity nftCommodity = deposit.getNftCommodity();
            //商品的NFTID
            int nftTokenId = nftCommodity.getNftTokenId();
            //该笔押金的金额
            int amount = deposit.getAmount();
            //退该笔押金
            if(effective&&nftTokenId==NFTId&&amount==depositAmount){
                depositNFTID = deposit.getNftId();
                depositUpdate = deposit;
                break;
            }
        }

       //退押金
        boolean transferRs = depositContract.backDeposit(userContractAddress, BigInteger.valueOf(depositNFTID)).send().isStatusOK();
        ArrayList<Deposit> depositArrayList;
        if(transferRs){
            //更新用户押金redis
            if(redisTemplate.boundValueOps(userId+"allDeposit").get()!=null){
                //redis中存在数据 更新
                logger.info("更新用户在redis中的押金数据");
                //先拿到redis中的数据
                List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps(userId+"allDeposit").get();
                depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
                //遍历
                for (Deposit deposit : depositArrayList) {
                    if(deposit.getNftId()== depositUpdate.getNftId()){
                        deposit.setEffective(false);
                        break;
                    }
                }
                //更新回redis
                redisTemplate.delete(userId+"allDeposit");
                redisTemplate.boundValueOps(userId+"allDeposit").set(depositArrayList);
            }

            if(redisTemplate.boundValueOps("allDeposit").get()!=null){
                //redis中存在数据 更新
                logger.info("更新用户在redis中的押金数据");
                //先拿到redis中的数据
                List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps("allDeposit").get();
                depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
                //遍历
                for (Deposit deposit : depositArrayList) {
                    if(deposit.getNftId()== depositUpdate.getNftId()){
                        deposit.setEffective(false);
                        break;
                    }
                }
                //更新回redis
                redisTemplate.delete("allDeposit");
                redisTemplate.boundValueOps("allDeposit").set(depositArrayList);
            }
        }
        return transferRs;
    }

    @Override
    public List<Deposit> queryAllDeposit() throws Exception {
//        redisTemplate.delete("allDeposit");
        ArrayList<Deposit> depositArrayList = new ArrayList<>();
        logger.info("data:{}",redisTemplate.boundValueOps("allDeposit").get());
        if(redisTemplate.boundValueOps("allDeposit").get()==null){
            //如果redis中不存在该数据
            logger.info("redis中不存在该数据");
            //查询所有用户
            HashMap<Object, Object> conditionMap = new HashMap<>();
            CommonResult commonResult = userService.queryUsers(conditionMap);
            List<Users> users = JSON.parseArray(JSON.toJSONString(commonResult.getData()), Users.class);
            //加载押金合约
            DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
            //遍历用户
            for (Users user : users) {
                //得到用户地址
                String contractAddress = user.getContractAddress();
                List<BigInteger> allNFTByAddress = DepositContractService.getAllNFTByAddress(contractAddress, depositContract);
                for (BigInteger nftByAddress : allNFTByAddress) {
                    Map<String, Object> nftMetaData = DepositContractService.getNFTMetaData(nftByAddress.intValue(), depositContract);
                    Deposit deposit = new Deposit();
                    deposit.setAmount(((BigInteger) nftMetaData.get("amount")).intValue());
                    deposit.setEffective((Boolean) nftMetaData.get("effective"));
                    deposit.setNftId(nftByAddress.intValue());
                    //商品的NFT ID
                    int nftId = ((BigInteger) nftMetaData.get("commodityNFTTokenId")).intValue();
                    deposit.setCommodityNFT(nftId);
                    depositArrayList.add(deposit);
                }
            }
            //加入redis
            redisTemplate.boundValueOps("allDeposit").set(depositArrayList);
        }else {
            //redis中有数据 直接从redis中拿即可
            logger.info("redis中存在该数据");
            List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps("allDeposit").get();
            logger.info("redis中的押金数据:{}",allDeposit);
            depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
        }
        return depositArrayList;
    }

    @Override
    public List<Deposit> queryByUserId(int userId) throws Exception {

//        redisTemplate.delete(userId+"allDeposit");
        //查询NFT元数据
        ArrayList<Deposit> depositArrayList = new ArrayList<>();

        if(redisTemplate.boundValueOps(userId+"allDeposit").get()==null){
            CommonResult commonResult = userService.queryById(userId);
            //转用户对象
            Users users = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Users.class);
            //得到地址
            String contractAddress = users.getContractAddress();
            //加载押金合约
            DepositContract depositContract = DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS);
            List<BigInteger> allNFTByAddress = DepositContractService.getAllNFTByAddress(contractAddress,DepositContractService.load(Constants.DEPOSIT_CONTRACT_ADDRESS));
            logger.info("该账户所持有的所有NFT:{}",allNFTByAddress);

            for (BigInteger nftByAddress : allNFTByAddress) {
                Map<String, Object> nftMetaData = DepositContractService.getNFTMetaData(nftByAddress.intValue(), depositContract);
                logger.info("NFT元数据:{}",nftMetaData);
                Deposit deposit = new Deposit();
                deposit.setAmount(((BigInteger)nftMetaData.get("amount")).intValue());
                deposit.setEffective((Boolean) nftMetaData.get("effective"));
                deposit.setNftId(nftByAddress.intValue());
                //商品的NFT ID
                int nftId = ((BigInteger) nftMetaData.get("commodityNFTTokenId")).intValue();
                CommonResult nftResult = commodityService.queryNFTByNftId(nftId);
                NFTCommodity nftCommodity = JSON.parseObject(JSON.toJSONString(nftResult.getData()), NFTCommodity.class);
                deposit.setNftCommodity(nftCommodity);
                depositArrayList.add(deposit);
            }
            //加入redis
            redisTemplate.boundValueOps(userId+"allDeposit").set(depositArrayList);

        }else{
            //redis中有数据 直接从redis中拿即可
            logger.info("redis中存在该用户的押金数据");
            List<Deposit> allDeposit = (List<Deposit>) redisTemplate.boundValueOps(userId+"allDeposit").get();
            logger.info("redis中的用户押金数据:{}",allDeposit);
            depositArrayList = (ArrayList<Deposit>) JSON.parseArray(JSON.toJSONString(allDeposit), Deposit.class);
        }

        logger.info("押金对象:",depositArrayList.toString());
        return depositArrayList;
    }
}
