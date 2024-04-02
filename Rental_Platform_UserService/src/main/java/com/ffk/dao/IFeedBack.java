package com.ffk.dao;

import com.ffk.pojo.FeedBack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @date 2021/3/7 21:15
 */
@Repository
public interface IFeedBack {

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
    List<FeedBack> queryFeedBack(Map map);

    /**
     * 更新
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
