package com.ffk.service;

import com.ffk.pojo.Recharge;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/5 0:09
 */
public interface IRechargeService {

    /**
     * 添加记录
     * @param recharge
     * @return
     */
    int addRecharge(Recharge recharge);

    /**
     * 查询
     * @param map
     * @return
     */
    List<Recharge> queryRecharge(Map map);

    /**
     * 按id删除
     * @param id
     * @return
     */
    int deleteRecharge(@Param("id") int id);
}
