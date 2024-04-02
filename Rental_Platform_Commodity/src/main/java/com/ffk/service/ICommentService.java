package com.ffk.service;

import com.ffk.pojo.Comment;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
public interface ICommentService {

    int addComment(Comment comment,int orderId);

    int updateComment(Comment comment);

    List<Comment> queryByNFTId(int nftId);

    int addAgree(int commentId);

    int addDeny(int commentId);


    /**
     * 多条件查询
     * @param map
     * @return
     */
    List<Comment> queryComment(Map map);

    /**
     * 开启一个定时任务 使得定时检测数据库中的评论 是否进行上链操作
     */
    void checkUpChain() throws Exception;

}
