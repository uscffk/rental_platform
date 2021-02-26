package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;
import java.io.Serializable;

/**
 * @author 房发科
 * @date 2021/2/18 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 返回给前端的通用JSON串
 */
public class CommonResult<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}
