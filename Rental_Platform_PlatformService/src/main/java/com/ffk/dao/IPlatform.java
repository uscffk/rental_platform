package com.ffk.dao;

import com.ffk.pojo.Platform;
import org.springframework.stereotype.Repository;

/**
 * @author 房发科
 * @date 2021/3/9 23:46
 */
@Repository
public interface IPlatform {
    /**
     * 根据用户名查询平台
     * @param username
     * @return
     */
    Platform queryPlatform(String username);
}
