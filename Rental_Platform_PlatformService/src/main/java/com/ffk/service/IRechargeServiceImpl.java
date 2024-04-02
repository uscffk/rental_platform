package com.ffk.service;

import com.ffk.dao.IRecharge;
import com.ffk.pojo.Recharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/4/5 0:10
 */
@Service
public class IRechargeServiceImpl implements IRechargeService{

    @Autowired
    private IRecharge recharges;
    @Override
    public int addRecharge(Recharge recharge) {
        return recharges.addRecharge(recharge);
    }

    @Override
    public List<Recharge> queryRecharge(Map map) {
        return recharges.queryRecharge(map);
    }

    @Override
    public int deleteRecharge(int id) {
        return recharges.deleteRecharge(id);
    }
}
