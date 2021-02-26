package com.ffk.service;

import com.ffk.dao.ICommodityCategory;
import com.ffk.pojo.CommodityCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public int addCategory(CommodityCategory commodityCategory) {
        return category.addCategory(commodityCategory);
    }

    @Override
    public int deleteCategory(int id) {
        return category.deleteCategory(id);
    }

    @Override
    public int updateCategory(CommodityCategory commodityCategory) {
        return category.updateCategory(commodityCategory);
    }

    @Override
    public List<CommodityCategory> queryCategory(Map map) {
        return category.queryCategory(map);
    }
}
