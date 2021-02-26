package com.ffk.dao;

import com.ffk.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 房发科
 * @date 2021/2/23 15:46
 */
@Repository
public interface IStock {
    /**
     * 增加库存记录
     * @param stock
     * @return
     */
    int insertStock(Stock stock);

    /**
     * 根据商品id删除库存记录
     * @param commodityId
     * @return
     */
    int deleteStock(int commodityId);

    /**
     * 减库存
     * @param commodityId 商品id
     * @param count 数量
     * @return
     */
    int decreaseStock(@PathVariable int commodityId, @PathVariable int count);

    /**
     * 加库存
     * @param commodityId 商品id
     * @param count  数量
     * @return
     */
    int addStock(int commodityId,int count);
    /**
     * 查询库存
     * @param commodityId
     * @return
     */
    Stock queryStock(int commodityId);

}
