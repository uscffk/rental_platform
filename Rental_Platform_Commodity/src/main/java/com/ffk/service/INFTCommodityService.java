package com.ffk.service;

import com.ffk.pojo.NFTCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
public interface INFTCommodityService {
    int addNFTCommodity(NFTCommodity nftCommodity) throws Exception;
    int updateNFTCommodity(NFTCommodity nftCommodity);
    List<NFTCommodity> queryNFTCommodity(Map map);
    NFTCommodity queryByNftId(@Param("nftId") int nftId);
    List<NFTCommodity> queryByCommodityId(@Param("id") int id);
}
