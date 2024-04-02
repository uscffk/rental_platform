package com.ffk.constant;

/**
 * @author 房发科
 * @date 2021/2/22 21:56
 */

/**
 * 定义一些项目中的常量
 */
public class Constants {

    /**
     * 成功码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 失败码
     */
    public static final int FAIL_CODE = 444;


    /**
     * 所有合约（押金、平台、商家、用户）均由外部平台账户来部署和调用。 商家 用户都是用合约账户
     */

    /**
     * 平台合约地址
     */
    public static final String PLATFORM_CONTRACT_ADDRESS = "0xb045ab28f9705b78852c124ccdf5a781d3e452d6";

    /**
     * 平台账户地址 这是外部账户地址 区分于平台的合约地址 这个账户在Ganache中选一个
     */
    public static final String PLATFORM_COUNT_ADDRESS = "0xc9B639F309229122685A6fEaD51845B8cF12b930";

    /**
     * 商家上架商品的佣金率
     */
    public static final double COMMISSION_RATE = 0.15;

    /**
     * 押金合约地址
     */
    public static final String DEPOSIT_CONTRACT_ADDRESS = "0xd1cfa502c85d9dbf8103954d3587e84cac82ba49";

    /**
     * 租赁权合约地址
     */
    public static final String RENTOWNERSHIPNFT_CONTRACT_ADDRESS = "0xa7fc6cab754bdc436c084c784a9d65df23554833";

    /**
     * 商品NFT合约地址
     */
    public static final String COMMODITYNFT_CONTRACT_ADDRESS = "0xd8826f81d68493832602f3d62e9db0624b089469";

    /**
     * 订单类型
     */
    public static final int SHARING_RENT = 0;    //共享租赁订单

    public static final int RENT_FOR_SALE = 1;   //以租代售订单

    public static final int RENT_BEFORE_BUY = 2; //先租后买订单

    /**
     * 订单状态
     */
    public static final int ORDER_FINISH = 1;//已完成

    public static final int ORDER_ONGOING = 0;//进行中

    public static final int NO_PERFORM_RENT = 2; //未履约 租赁平台v2.0舍弃该类型

    public static final int BLACK_USER_ORDER = 3;//黑名单订单

    public static final int HAVE_BUY = 4;//已购买

    /**
     * 是否拥有租赁权限
     */
    public static final int HAS_RENT_QULIFICATION = 1;//拥有租赁权限

    public static final int NO_HAS_RENT_QULIFICATION = 0;//无租赁权限

    public static final int SUPPORT = 1;  //租赁平台v2.0舍弃该类型

    /**
     * 免押
     */
    public  static final int NOT_NEED_DEPOSIT = 1000; //免押的信用值

    /**
     * 计费跨度
     */
    public static final Long HOUR_TIME = 3600000L;       //小时
    public static final Long DAY_TIME = HOUR_TIME * 24;  //天
    public static final Long MONTH_TIME = DAY_TIME * 30; //月

    /**
     * 计费方式
     */
    public static final Integer HOUR_RENT = 0;  //按小时
    public static final Integer DAY_RENT = 1;   //按天
    public static final Integer MONTH_RENT = 2; //按月

    /**
     * 订单完成后下一期支付时间 这个时间代表还清了
     */
    public static final String ORDER_END_NEXTPAY = "2100-12-22 22:22:22";

    /**
     * 收益来源
     */
    public static final int PROFIT_FROM_MANUFACTURER = 0;  //来源于商家上架物品
    public static final int PROFIT_FROM_Bid = 0;  //来源于商家竞拍


    /**
     * 利率 先租后买租赁模式中，在购买之前花费的租赁费用给你打几折 因为在购买之前你也用了产品
     */
    public static final double RENT_BEFORE_BUY_RATE = 0.6;

    /**
     * 租赁商品的状态
     */
    public static final int NFT_FREE = 0;   //正常供应状态即空闲
    public static final int NFT_FIXING = 1; //代表维护状态
    public static final int NFT_USING = 2;  //代表使用中
    public static final int NFT_SELLED = 3; //代表已售出

    /**
     * 反馈是给平台的标志 反馈表中manufacturerId = 0
     */
    public static final int FEEDBACK_PLATFORM = 0;

    /**
     * 充值身份
     */
    public static final int RECHARGE_BY_MANU = 1; //商家充值
    public static final int RECHARGE_BY_USER = 0; //用户充值


    /**
     * 归还半径 用户在和租赁站点的位置距离为多少时可以退租成功 单位是km 因为在微信开发者工具上定位根本就是定不准的 只能这样去模拟了
     */
    public static final double BACKRENT_RADIUS = 3.0;
}
