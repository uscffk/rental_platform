package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommodityCategory;
import com.ffk.pojo.CommonResult;
import com.ffk.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author 房发科
 * @date 2021/2/23 16:48
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询全部
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public CommonResult getCategory(){
        System.out.println(1);
        HashMap<String, String> map = new HashMap<>(1);
        try {
            List<CommodityCategory> commodityCategories = categoryService.queryCategory(map);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",commodityCategories);
        }catch (Exception e){
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }

    }

    /**
     * 根据商品id查询分类
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getCategoryByComId(@PathVariable int id){
        HashMap<String, String> map = new HashMap<>(1);
        map.put("id",String.valueOf(id));
        try {
            List<CommodityCategory> commodityCategories = categoryService.queryCategory(map);
            return new CommonResult(Constants.SUCCESS_CODE,"成功",commodityCategories);
        }catch (Exception e){
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public CommonResult updateCategory(@RequestBody CommodityCategory category){
        if(category!=null){
            try {
                int rs = categoryService.updateCategory(category);
                if(rs>0){
                    return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
                }
            }catch (Exception e){
                return new CommonResult(Constants.FAIL_CODE,"失败",null);
            }
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult addCategory(@RequestBody CommodityCategory category){
        try {
            int rs = categoryService.addCategory(category);
            if(rs>0){
                return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
            }
        }catch (Exception e){
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    public CommonResult deleteCategory(@PathVariable int id){
        try {
            int rs = categoryService.deleteCategory(id);
            if(rs>0){
                return new CommonResult(Constants.SUCCESS_CODE,"成功",null);
            }
        }catch (Exception e){
            return new CommonResult(Constants.FAIL_CODE,"失败",null);
        }
        return new CommonResult(Constants.FAIL_CODE,"失败",null);
    }

    /**
     * 查种类数目
     * @return
     */
    @RequestMapping(value = "/queryTotal",method = RequestMethod.POST)
    public CommonResult queryTotal(){
        try {
            int total = categoryService.getTotal();
            return new CommonResult(Constants.SUCCESS_CODE,"ok",total);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
