package com.ffk.service;

import com.ffk.pojo.Stock;
import org.apache.ibatis.annotations.Param;

/**
 * @author 房发科
 * @date 2021/2/23 15:51
 */
public interface IStockService {
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
    int decreaseStock(@Param("commodityId") int commodityId, @Param("count") int count);

    /**
     * 加库存
     * @param commodityId 商品id
     * @param count  数量
     * @return
     */
    int addStock(@Param("commodityId") int commodityId, @Param("count") int count);

    /**
     * 查询库存
     * @param  commodityId
     * @return
     */
    Stock queryStock(int commodityId);

    /**
     * 更新库存
     * @param stock
     * @return
     */
    int updateStock(Stock stock);
}
