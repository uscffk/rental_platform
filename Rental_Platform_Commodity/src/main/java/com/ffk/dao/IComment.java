package com.ffk.dao;

import com.ffk.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Mapper
@Repository
public interface IComment {

    int addComment(Comment comment);

    int updateComment(Comment comment);

    List<Comment> queryByNFTId(@Param("nftId") int nftId);

    Comment queryById(@Param("id") int id);

    /**
     * 多条件查询
     * @param map
     * @return
     */
    List<Comment> queryComment(Map map);
}
