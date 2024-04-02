package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.Comment;
import com.ffk.pojo.CommonResult;
import com.ffk.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/3/7
 */
@RequestMapping("/comments")
@RestController
public class CommentCtroller {
    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public CommonResult addComment(@RequestParam("content") String content,
                                   @RequestParam("nftId") int nftId,
                                   @RequestParam("orderId") int orderId){
        Comment comment = new Comment();
        comment.setNftId(nftId);
        comment.setContent(content);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String format = simpleDateFormat.format(new Date());
        comment.setTime(format);
        try {
            int rs = commentService.addComment(comment,orderId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/addAgree",method = RequestMethod.POST)
    public CommonResult addAgree(@RequestParam("commentId") int commentId){
        try{
            int rs = commentService.addAgree(commentId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/addDeny",method = RequestMethod.POST)
    public CommonResult addDeny(@RequestParam("commentId") int commentId){
        try{
            int rs = commentService.addDeny(commentId);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }

    @RequestMapping(value = "/queryComment",method = RequestMethod.POST)
    public CommonResult queryComments(@RequestBody Map map){
        try{
            List<Comment> comments = commentService.queryComment(map);
            return new CommonResult(Constants.SUCCESS_CODE,"ok",comments);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }




}
