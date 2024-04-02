package com.ffk.service;


/**
 * @author 房发科
 * @date 2021/3/6 17:07
 */
public interface IBackCommodityService {
    int backCommodity(int orderId,String longitude,String latitude,int nftId) throws Exception;

    /**
     * 判断用户是否在归还站点之内
     * @param longitude   用户当前位置的经度
     * @param latitude    用户当前位置的纬度
     * @return
     */
    boolean judgeIsInBackLocation(String longitude,String latitude,int nftId);

}
