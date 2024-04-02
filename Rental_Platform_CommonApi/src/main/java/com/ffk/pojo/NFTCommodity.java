package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
public class NFTCommodity {


    //商品的nft tokenId
    int nftTokenId;


    /**
     * 经度
     */
    String longitude;

    /**
     * 纬度
     */
    String latitude;

    /**
     * 详细位置描述
     */
    String detailLoc;

    //商品id
    int goodsId;

    //状态
    int status;

    //评论
    List<Comment> comments;
    //所属商品
    Commodity commodity;
}
