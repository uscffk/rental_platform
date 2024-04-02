package com.ffk.service;

import com.ffk.pojo.Deposit;

import java.util.List;

/**
 * @author 房发科
 * @date 2021/3/17 21:50
 */
public interface DepositService {
    /**
     * 根据订单号退押金
     * @param orderAddress
     */
    boolean backDeposit(int orderAddress) throws Exception;

    /**
     * 查询所有押金
     * @return
     */
    List<Deposit> queryAllDeposit() throws Exception;

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    List<Deposit> queryByUserId(int userId) throws Exception;

    /**
     * 交押金
     * @param userAddress
     * @param commodityNFTToken
     * @param amount
     * @return
     */
    boolean mintDepositNFT(String userAddress,int commodityNFTToken,int amount) throws Exception;



}
