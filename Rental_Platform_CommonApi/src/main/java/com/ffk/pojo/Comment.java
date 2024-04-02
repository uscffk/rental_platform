package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
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
    int userId;
    Users users;

}
