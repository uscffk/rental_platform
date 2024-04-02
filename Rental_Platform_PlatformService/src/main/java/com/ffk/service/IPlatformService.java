package com.ffk.service;

import com.ffk.pojo.Platform;
import com.ffk.pojo.Recharge;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/9 23:51
 */
public interface IPlatformService {

    /**
     * 根据用户名查询平台
     * @param username
     * @return
     */
    Platform queryPlatform(String username);


    /**
     *
     * @param id
     * @param amount 账户
     * @param from 来源
     * @return
     * @throws Exception
     */
    boolean recharge(int id, int amount,int from) throws Exception;

    /**
     *
     * @param id 用户id
     * @param amount 金额
     * @param from 来源
     * @return
     * @throws Exception
     */
    boolean getRMB(int id, int amount,int from) throws Exception;

    /**
     * 查询余额
     * @return 余额
     */
    String queryMoney() throws Exception;


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Recharge>  queryRecord(Map map);

    /**
     * 平台外部账户给平台合约账户充钱
     * @param amount
     * @return
     */
    boolean rechargeToSelf(int amount) throws Exception;


    /**
     * 把合约账户中钱转到外部账户
     * @return
     */
    boolean transferToExteneralAccount() throws Exception;

}
