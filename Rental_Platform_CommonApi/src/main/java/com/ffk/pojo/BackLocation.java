package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @create 2022/4/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//可以链式操作，默认是false
/**
 * 商品的归还站点  比如共享单车在笃五 三省园都有归还站点
 */
public class BackLocation {
    int id;
    int commodityId;
    String longitude;
    String latitude;
    String detailLoc;
}
