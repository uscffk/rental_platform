package com.ffk.service;

import com.ffk.pojo.Order;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/3/17 23:24
 */
public interface IRentService {
//    // nextPay到期的id
//    List<Integer> getDeadLine();

    /** 期数减一 */
    boolean decreaseTime(Integer orderId);

    /** 总期数 */
    Integer getTotalTime(Integer orderId);

    /** 返回当前的期数 */
    Integer getCurrTime(Integer orderId);

    /** 增加支付的总额,tptalPay */
    boolean addTotalPay(Integer orderId, Integer payment);

    /** 订单结束，设置finishTime */
    boolean finishOrder(Integer orderId);

    /**
     * 改变订单状态（已完成、进行中、分期中、违约）
     *
     * @return: 成功与否
     */
    boolean changeStatus(Integer orderId, Integer status);

    /**
     * 通过ID查询Userid
     *
     * @param orderId 主键
     * @return id
     */
    int queryUserIdById(Integer orderId);

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    Order queryById(Integer orderId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Order> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    int insert(Order order);

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    Order update(Order order);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
//    boolean deleteById(Integer orderId);
    boolean buyCommodity(int money,int orderId,int userId) throws Exception;

    /**
     *
     * @param orderId
     * @param time 续租期数
     * @param userId 用户名ID
     */
    String reRent(int orderId,int time,int userId) throws Exception;

    /**
     * 判断页面跳转
     * @param nftId
     * @param userId
     * @return
     */
    int judge(int nftId, int userId) throws Exception;

    /**
     * 计算先租后买需要支付的钱
     * @param orderId
     * @param userId
     * @return
     */
    int calculateBuyMoney(int orderId,int userId) throws Exception;
}
