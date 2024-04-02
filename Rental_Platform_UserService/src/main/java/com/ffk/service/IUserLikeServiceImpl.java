package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.ffk.dao.IUserLike;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.UserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 房发科
 * @date 2021/4/9 17:07
 */
@Service
public class IUserLikeServiceImpl implements IUserLikeService{

    @Autowired
    private IUserLike userLikes;

    @Autowired
    private ICommodityService commodityService;

    @Override
    public int addUserLike(UserLike userLike) {
        return userLikes.addUserLike(userLike);
    }

    @Override
    public int deleteUserLike(int id) {
        return userLikes.deleteUserLike(id);
    }

    @Override
    public List<UserLike> queryUserLike(int userId) {
        //在业务层进行拼接

        List<UserLike> userLikes = this.userLikes.queryUserLike(userId);
        //遍历
        for (UserLike userLike : userLikes) {
            //查询商品id
            int comId = userLike.getComId();
            CommonResult commonResult = commodityService.queryCommodityById(comId);
            Commodity commodity = JSON.parseObject(JSON.toJSONString(commonResult.getData()), Commodity.class);
            //进行拼接
            userLike.setCommodity(commodity);
        }

        return userLikes;
    }
}
