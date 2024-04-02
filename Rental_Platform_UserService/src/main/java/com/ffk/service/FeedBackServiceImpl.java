package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.constant.Constants;
import com.ffk.controller.FileUploadController;
import com.ffk.dao.IFeedBack;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.FeedBack;
import com.ffk.pojo.Manufacturer;
import com.ffk.pojo.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/7 21:21
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private IFeedBack feedBacks;
    @Autowired
    private IManufacturerService  manufacturerService;
    @Autowired
    private UsersService usersService;
    @Override
    public int addFeedBack(FeedBack feedBack) {
        return feedBacks.addFeedBack(feedBack);
    }

    Logger logger = LoggerFactory.getLogger(FeedBackServiceImpl.class);

    @Override
    public List<FeedBack> queryFeedBack(Map map) throws Exception {

        List<FeedBack> feedBacks = this.feedBacks.queryFeedBack(map);
        //拼接返回结果
        for (FeedBack feedBack : feedBacks) {
            int manufacturerId = feedBack.getManufacturerId();
            //如果是给平台的
            if (manufacturerId != Constants.FEEDBACK_PLATFORM){
                CommonResult commonResult = manufacturerService.queryManufacturerById(manufacturerId);
                Manufacturer manufacturer = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Manufacturer.class);
                feedBack.setManufacturer(manufacturer);
            }

            int userId = feedBack.getUserId();
            Users user = usersService.queryById(userId);
            feedBack.setUsers(user);
        }
        logger.info("feebacks:{}",feedBacks);
        return feedBacks;
    }

    @Override
    public int updateFeedBack(FeedBack feedBack) {
        return feedBacks.updateFeedBack(feedBack);
    }

    @Override
    public int deleteFeedBack(int id) {
        return feedBacks.deleteFeedBack(id);
    }
}
