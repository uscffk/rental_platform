package com.ffk.service;

import com.ffk.pojo.FeedBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/7 21:20
 */
public interface FeedBackService {
    /**
     * 添加反馈
     * @param feedBack
     * @return
     */
    int addFeedBack(FeedBack feedBack);

    /**
     * 查询反馈
     * @param map
     * @return
     */
    List<FeedBack> queryFeedBack(Map map) throws Exception;

    /**
     * 修改反馈
     * @param feedBack
     * @return
     */
    int updateFeedBack(FeedBack feedBack);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteFeedBack(@Param("id") int id);
}
