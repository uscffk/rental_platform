package com.ffk.pojo;

import com.ffk.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 房发科
 * @date 2021/2/26 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class Order {

    /**
     * 订单id
     */
    private int orderId;
    /**
     * 押金金额
     */
    private int deposit;


    /**
     * 厂商id
     */
    private int manufacturerId;

    /**
     * 订单状态
     */
    private int status;

    /**
     * 订单类型
     * 0 共享租赁
     * 1 以租代售
     * 2 先租后买
     */
    private int orderType;

    /**
     * 订单完成时间
     */
    private String finishTime;

    /**
     * 下单时间
     */
    private String creatTime;

    /**
     * 体验总期数
     */
    private int totalTime;

    /**
     * 当前期数
     */
    private int currTime;

    /**
     * 该订单需支付金额
     */
    private int sumMoney;

    /**
     * 实际支付金额
     */
    private int actualPay;

    /**
     * 下一次支付时间
     */
    private String nextPay;

    /**
     * 付费方式
     */
    private int billMethod;

    /**
     * 是否已评价订单
     */
    private int comment;

    /**
     * 商品
     */
    private Commodity commodity;

    public Order(Commodity commodity, int method, int totalTime, int billMethod, int sumMoney) {
        this.setManufacturerId(commodity.getManufacturerId());
        this.setStatus(Constants.ORDER_ONGOING);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar calendar = Calendar.getInstance();
        Date dates = new Date(calendar.getTimeInMillis());
        String format = simpleDateFormat.format(dates);
        this.setCreatTime(format);
        this.setTotalTime(totalTime);
        this.setOrderType(method);
        this.setDeposit(commodity.getDeposit());
        this.setCurrTime(1);
        this.setBillMethod(billMethod);
        this.setSumMoney(sumMoney);

        //只有以租代售订单才有nextPay
        if(method==Constants.RENT_FOR_SALE){
            //设置总金额
            this.setSumMoney(commodity.getSellPrice());
            //判断下一次还款日期
            if (billMethod == Constants.HOUR_RENT){
                //按小时计费
                Date date = new Date(calendar.getTimeInMillis() + Constants.HOUR_TIME);
                String time = simpleDateFormat.format(date);
                System.out.println(time);
                this.setNextPay(time);
            }else if(billMethod == Constants.DAY_TIME){
                //按天计费
                Date date = new Date(calendar.getTimeInMillis() + Constants.DAY_TIME);
                String time = simpleDateFormat.format(date);
                this.setNextPay(time);
            }else {
                //按月计费
                Date date = new Date(calendar.getTimeInMillis() + Constants.MONTH_TIME);
                String time = simpleDateFormat.format(date);
                this.setNextPay(time);
            }
        }else{
            this.setNextPay(Constants.ORDER_END_NEXTPAY);
        }




    }
}
