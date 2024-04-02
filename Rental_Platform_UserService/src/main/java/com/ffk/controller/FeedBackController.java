package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.FeedBack;
import com.ffk.service.FeedBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/7 21:13
 */
@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;

    Logger logger = LoggerFactory.getLogger(FeedBackController.class);
    /**
     * 添加
     * @param feedBack
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult addFeedBack(@RequestBody FeedBack feedBack){
        try{
            int rs = feedBackService.addFeedBack(feedBack);
            if(rs > 0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 查询
     * @param parameterMap
     * @return
     */
    @RequestMapping(value = "/queryFeedback",method = RequestMethod.POST)
    public CommonResult queryFeedBack(@RequestBody Map parameterMap){
        if (parameterMap.size()>=0){
            try {
                List<FeedBack> feedBacks = feedBackService.queryFeedBack(parameterMap);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",feedBacks);
            }catch (Exception e){
                e.printStackTrace();
                return new CommonResult(Constants.FAIL_CODE,"error",null);
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 更新
     * @param feedBack
     * @return
     */
    @RequestMapping(value = "/updateFeedBack",method = RequestMethod.POST)
    public CommonResult updateFeedBack(@RequestBody FeedBack feedBack){
        try {
            logger.info("要更新的feedback:{}",feedBack);
            int rs = feedBackService.updateFeedBack(feedBack);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE, "error");
    }

    /**
     * 删除反馈
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteFeedBack",method = RequestMethod.POST)
    public CommonResult deleteFeedBack(@RequestParam("id") int id){
        try{
            int rs = feedBackService.deleteFeedBack(id);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
