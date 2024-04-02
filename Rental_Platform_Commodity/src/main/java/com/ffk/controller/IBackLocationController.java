package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.BackLocation;
import com.ffk.pojo.CommonResult;
import com.ffk.service.IBackLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 房发科
 * @create 2022/4/11
 */
@RestController
@RequestMapping("/backLocation")
public class IBackLocationController {

    @Autowired
    private IBackLocationService backLocationService;

    @RequestMapping(value = "/queryAll",method = RequestMethod.POST)
    public CommonResult queryAll(){
        try {
            List<BackLocation> backLocations = backLocationService.queryAll();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",backLocations);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 按商品id查 不是按这个表的主键id去查
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryById",method = RequestMethod.POST)
    public CommonResult queryById(@RequestParam("id") int id){
        try {
            List<BackLocation> backLocations = backLocationService.queryById(id);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",backLocations);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 更新站点
     * @param backLocation
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult update(@RequestBody BackLocation backLocation){
        try {
            int rs = backLocationService.updateBackLocation(backLocation);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 添加站点
     * @param backLocation
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult add(@RequestBody BackLocation backLocation){
        try{
            int rs = backLocationService.addBackLocation(backLocation);
            return new CommonResult(Constants.SUCCESS_CODE,"error",rs);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    /**
     * 按商家id查询
     * @param mid
     * @return
     */
    @RequestMapping(value = "/queryByMid",method = RequestMethod.POST)
    public CommonResult queryByManufacturerId(@RequestParam("mid") int mid){
        try {
            List<BackLocation> backLocations = backLocationService.queryByMId(mid);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",backLocations);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
