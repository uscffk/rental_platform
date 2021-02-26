package com.ffk.dao;

import com.ffk.pojo.CommodityCategory;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/23 16:33
 */
@Repository
public interface ICommodityCategory {
    /**
     * 增加商品种类
     * @param commodityCategory
     * @return
     */
    int addCategory(CommodityCategory commodityCategory);

    /**
     * 根据id删除种类
     * @param id
     * @return
     */
    int deleteCategory(int id);

    /**
     * 修改商品种类
     * @param commodityCategory
     * @return
     */
    int updateCategory(CommodityCategory commodityCategory);

    /**
     * 查询种类 按id查 查询全部
     * @param map
     * @return
     */
    List<CommodityCategory> queryCategory(Map map);
}
