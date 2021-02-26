package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.DeclareAnnotation;

/**
 * @author 房发科
 * @date 2021/2/25 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityCategory {

    /**
     * 种类id
     */
    private int id;

    /**
     * 种类名称
     */
    private String name;

    /**
     * 种类描述
     */
    private String discription;
}
