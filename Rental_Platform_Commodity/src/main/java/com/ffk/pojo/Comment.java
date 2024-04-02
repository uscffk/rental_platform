package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 商品评论
 */
public class Comment {
    int id;
    int nftId;
    String content;
    int agree;
    int deny;
    int upchain;
    String time;
//    int userId;
    //不能关联用户 不然不是能通过评论表找到哪个NFT被哪个用户租赁了 和隐私保护不符合
//    Users users;

}
