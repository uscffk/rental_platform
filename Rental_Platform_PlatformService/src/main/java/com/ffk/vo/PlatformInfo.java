package com.ffk.vo;

import com.ffk.pojo.Profit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 房发科
 * @create 2022/3/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformInfo {
    int userNumber;
    int manuNumber;
    int typeNumber;
    int profitNumber;
    //近一周的收益
    int[] profit;
    //订单类型数目
    int[] orderType;

}
