package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 房发科
 * @date 2021/3/9 23:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//开启链式编程 这个很舒服啊
@Accessors(chain = true)
public class Platform {

    /**
     * 用户名
     */
    private String  username;

    /**
     * 密码
     */
    private String  password;

}
