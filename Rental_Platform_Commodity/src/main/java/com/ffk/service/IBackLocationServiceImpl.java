package com.ffk.service;

import com.ffk.dao.IBackLocation;
import com.ffk.pojo.BackLocation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 房发科
 * @create 2022/4/11
 */
@Service
public class IBackLocationServiceImpl implements IBackLocationService{

    @Autowired
    private IBackLocation  backLocations;
    @Autowired
    private ICommodityService commodityService;

    @Override
    public int addBackLocation(BackLocation backLocation) {
        return backLocations.addBackLocation(backLocation);
    }

    @Override
    public int deleteBackLocation(int id) {
        return backLocations.deleteBackLocation(id);
    }

    @Override
    @HystrixCommand
    public List<BackLocation> queryAll() {
        return backLocations.queryAll();
    }

    @Override
    @HystrixCommand
    public BackLocation queryByPkeyId(int id) {
        return backLocations.queryByPkeyId(id);
    }

    @Override
    public List<BackLocation> queryByMId(int mid) {
        List<Integer> comIds = commodityService.queryCommodityIdByMId(mid);
        List<BackLocation> rs = new ArrayList<>();
        for (Integer comId : comIds) {
            List<BackLocation> backLocations = this.queryById(comId.intValue());
            //我这写法是真的优点毛病了
            for (BackLocation backLocation : backLocations) {
                rs.add(backLocation);
            }

        }
        return rs;
    }

    @Override
    @HystrixCommand
    public List<BackLocation> queryById(int id) {
        return backLocations.queryById(id);
    }

    @Override
    public int updateBackLocation(BackLocation backLocation) {
        return backLocations.updateBackLocation(backLocation);
    }
}
