package com.ffk.service;

import com.ffk.pojo.Order;
import java.util.List;
import java.util.Map;


/**
 * @author 房发科
 * @date 2021/2/26 16:18
 */
public interface IOrderService {
    /**
     * 更新订单
     * @param order
     * @return
     */
    int  updateOrder(Order order);

    /**
     * 查询订单
     * @param map
     * @return
     */
    List<Order> queryOrder(Map map) throws Exception;

    /**
     * 新增订单
     * @param order
     * @return
     */
    int addOrder(Order order) throws Exception;

    /**
     * 按id查询订单
     * @param id
     * @return
     */
    Order queryById(int id);

    /**
     * 根据NFTID 找订单
     * @param nftID
     * @return
     */
    Order queryOrderByNFTID(int nftID) throws Exception;

    /**
     * 根据订单ID查NFT ID
     * @param orderId
     * @return
     */
    int queryNFTIDByOrderId(int orderId) throws Exception;

}
