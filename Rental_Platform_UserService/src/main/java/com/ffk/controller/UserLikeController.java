package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.UserLike;
import com.ffk.service.IUserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/4/9 17:10
 */
@RestController
@RequestMapping("/userLike")
public class UserLikeController {

    @Autowired
    private IUserLikeService userLikeService;

    /**
     * 查询
     * @param userId
     * @return
     */
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public CommonResult queryUserLike(@RequestParam("userId") int userId){
        try {
            List<UserLike> userLikes = userLikeService.queryUserLike(userId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",userLikes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     *
     * @param id 按收藏id删除
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult deleteUserLike(@RequestParam("id") int id){
        try {
            int rs = userLikeService.deleteUserLike(id);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }

    /**
     * 添加
     * @param userLike
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult addUserLike(@RequestBody UserLike userLike){
        try {
            int rs = userLikeService.addUserLike(userLike);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error");
    }



}
