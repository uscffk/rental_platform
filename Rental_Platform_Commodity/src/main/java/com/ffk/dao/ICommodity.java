package com.ffk.dao;

import com.ffk.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/2/10 23:58
 */
@Repository
public interface ICommodity {

    /**
     * 添加商品
     * @param commodity
     * @return
     */
    int addCommodity(Commodity commodity);

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    int deleteCommodity(int id);

    /**
     * 更新商品
     * @param commodity
     * @return
     */
    int updateCommodity(Commodity commodity);

    /**
     * 查询商品
     * @param map
     * @return
     */
    List<Commodity> queryCommodity(Map map);
}
