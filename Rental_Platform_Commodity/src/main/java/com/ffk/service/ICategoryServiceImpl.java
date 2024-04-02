package com.ffk.service;

import com.ffk.dao.ICommodityCategory;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommodityCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/23 16:43
 */
@Service
public class ICategoryServiceImpl implements ICategoryService{
    @Autowired
    private ICommodityCategory category;

    @Autowired
    private ICommodityService commodityService;

    Logger logger = LoggerFactory.getLogger(ICategoryServiceImpl.class);

    @Override
    public int getTotal() {
        return category.getTotal();
    }

    /**
     * 添加商品种类（平台）
     * @param commodityCategory
     * @return
     */
    @Override
    public int addCategory(CommodityCategory commodityCategory) {
        return category.addCategory(commodityCategory);
    }

    /**
     * 删除商品种类（此功能暂不予实现）
     * @param id
     * @return
     */
    @Override
    public int deleteCategory(int id) {
        return category.deleteCategory(id);
    }

    /**
     * 前台传入表单 springmvc将其封装为CommodityCategory对象（平台）  注意数据一致性（此功能暂不予实现）
     * 与之相关联的表有商品表、
     * @param commodityCategory
     * @return
     */
    @Override
    public int updateCategory(CommodityCategory commodityCategory) {
        return category.updateCategory(commodityCategory);
    }

    /**
     * 通过map实现任意条件组合查询 可查询所有种类 可按商品id查询
     * @param map
     * @return
     */
    @Override
    public List<CommodityCategory> queryCategory(Map map) {
        List<CommodityCategory> commodityCategories = category.queryCategory(map);
        logger.info("商品种类:{}",commodityCategories);

        //遍历commodityCategories拼接对应种类的商品
//        for (CommodityCategory commodityCategory : commodityCategories) {
//            //获得种类Id
//            int id = commodityCategory.getId();
//            System.out.println(commodityCategory.getName()+"种类"+id);
//            //根据id查询商品
//            HashMap<Object, Object> objectObjectHashMap = new HashMap<>(1);
//            objectObjectHashMap.put("category",id);
//            //查询出商品
//            List<Commodity> commodities = commodityService.queryCommodity(objectObjectHashMap);
//            System.out.println("商品:"+commodities);
//            //赋值
//            commodityCategory.setCommodityList(commodities);
//        }


        return commodityCategories;
    }
}
