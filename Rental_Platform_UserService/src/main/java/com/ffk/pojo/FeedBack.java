package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author 房发科
 * @date 2021/3/7 21:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack implements Serializable {
    /**
     * 反馈id
     */
    private int id;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈人
     */
    private int userId;

    /**
     * 商家
     */
    private int manufacturerId;

    /**
     * 图片
     */
    private String picture;

    /**
     * 厂商
     */
    Manufacturer manufacturer;

    /**
     * 用户
     */
    Users users;
}
