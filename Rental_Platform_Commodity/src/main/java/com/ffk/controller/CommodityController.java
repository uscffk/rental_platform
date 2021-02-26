package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.service.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/18 15:53
 */
@RestController
@RequestMapping("/commodities")
public class CommodityController {
    @Autowired
    private ICommodityService commodityService;

    /**
     * 添加商品
     * @param commodity
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public CommonResult addCommodity(Commodity commodity){
        int rs = commodityService.addCommodity(commodity);
        //添加成功
        if(rs > 0){
            return new CommonResult(Constants.SUCCESS_CODE,"添加商品成功",rs);
        }
        //添加失败
        return new CommonResult(Constants.FAIL_CODE,"添加商品失败",null);
    }

    /**
     * 查询商品
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public CommonResult queryCommodity(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if(parameterMap.size()>=0){
            HashMap<String,String> conditionMap = new HashMap<>();
            for (String condition : parameterMap.keySet()) {
                //转码解决get传中文导致乱码问题
                conditionMap.put(condition, URLDecoder.decode(parameterMap.get(condition)[0], "UTF-8"));
            }
            try {
                List<Commodity> commodities = commodityService.queryCommodity(conditionMap);
                return new CommonResult(Constants.SUCCESS_CODE,"查询成功",commodities);
            }catch (Exception e){
                return new CommonResult(Constants.FAIL_CODE,"查询失败",null);
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"查询失败",null);
    }

}
