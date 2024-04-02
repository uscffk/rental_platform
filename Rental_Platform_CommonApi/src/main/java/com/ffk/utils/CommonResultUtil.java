package com.ffk.utils;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @create 2022/4/29
 * 用来优化代码的  其实并无太大用处 过度设计了属于是 先放着
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)//可以链式操作，默认是false
//public class CommonResultUtil {
//    public static CommonResult success(Object object) {
//        CommonResult result = new CommonResult();
//        result.setCode(Constants.SUCCESS_CODE);
//        result.setMessage("ok");
//        result.setData(object);
//        return result;
//    }
//
//    public static CommonResult success() {
//        return success(null);
//    }
//
//    public static CommonResult error(Integer code, String msg) {
//        CommonResult result = new CommonResult();
//        result.setCode(code);
//        result.setMessage(msg);
//        return result;
//    }
//}
