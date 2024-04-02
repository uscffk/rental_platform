package com.ffk.dao;

import com.ffk.pojo.NFTCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Mapper
@Repository
public interface INFTCommodity {
    int addNFTCommodity(NFTCommodity nftCommodity);
    int updateNFTCommodity(NFTCommodity nftCommodity);
    List<NFTCommodity> queryNFTCommodity(Map map);
    NFTCommodity queryByNftId(@Param("nftId") int nftId);
    List<NFTCommodity> queryByCommodityId(@Param("id") int id);
}
