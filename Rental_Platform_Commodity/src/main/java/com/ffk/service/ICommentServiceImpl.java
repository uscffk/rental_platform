package com.ffk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ffk.constant.Constants;
import com.ffk.contract.CommodityNFTContract;
import com.ffk.contract.service.CommodityContractService;
import com.ffk.dao.IComment;
import com.ffk.ipfs.IpfsUtil;
import com.ffk.pojo.Comment;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.Order;
import com.ffk.pojo.Users;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 房发科
 * @create 2022/2/10
 */
@Service
@EnableScheduling //开启定时任务  处理评论上链
public class ICommentServiceImpl implements ICommentService{

    @Autowired
    private IComment commentDao;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IpfsUtil ipfsUtil;

    Logger logger = LoggerFactory.getLogger(ICommentServiceImpl.class);

    //每10s执行一次
    @Scheduled(cron = "0/10 * * * * ? ")
    @Override
    public void checkUpChain() throws Exception {
        //拿到所有还未上链的评论
        HashMap<Object, Object> conditionMap = new HashMap<>();
        conditionMap.put("upChain",0);
        List<Comment> comments = commentDao.queryComment(conditionMap);
        logger.info("未上链的评论:{}",comments);
        //遍历
        for (Comment comment : comments) {
            int agree = comment.getAgree();
            int deny = comment.getDeny();
            //需要支持大于100才行
            if(agree>=100&&deny!=0){
                //支持与反对之比大于4
                if(agree/deny>=4){
                    //上链 1.先拿到原先评论的地址
                    int nftId = comment.getNftId();
                    //调用商品合约
                    CommodityNFTContract commodityNFTContract = CommodityContractService.load(Constants.COMMODITYNFT_CONTRACT_ADDRESS);
                    String commodityComment = CommodityContractService.getCommodityComment(nftId, commodityNFTContract);
                    //空的 直接传即可
                    String content = comment.getContent();
                    logger.info("上链的评论:{}",content);
                    //判断是否为空
                    if(commodityComment.equals("")){
                        //返回地址的哈希值  +是定界符 区分不同的评论
                        String hash = ipfsUtil.add(content + "+");
                        //写回
                        CommodityContractService.setCommodityComment(hash,nftId,commodityNFTContract);

                    }else{
                        //不为空 要先去拿地址 拿到之前的评论
                        String oldComment = ipfsUtil.query(commodityComment);
                        String newComment = oldComment + content + "+";
                        //传到IPFS  返回一个新的 哈希值
                        String newHash = ipfsUtil.add(newComment);
                        //写回
                        CommodityContractService.setCommodityComment(newHash,nftId,commodityNFTContract);
                    }

                    //更改为已上链
                    comment.setUpchain(1);
                    //更新
                    commentDao.updateComment(comment);
                }
            }
        }

    }

    @Override
    public int addDeny(int commentId) {
        Comment comment = commentDao.queryById(commentId);
        comment.setAgree(comment.getAgree()-1);
        return commentDao.updateComment(comment);
    }

    @Override
    public List<Comment> queryComment(Map map) {
        return commentDao.queryComment(map);
    }

    @Override
    public int addAgree(int commentId) {
        Comment comment = commentDao.queryById(commentId);
        comment.setAgree(comment.getAgree()+1);
        return commentDao.updateComment(comment);
    }

    @GlobalTransactional
    @Override
    public int addComment(Comment comment,int orderId) {

        //更新订单状态 已评论
        int rs = commentDao.addComment(comment);
        if(rs!=0){
            //更新订单
            CommonResult orderResult = orderService.queryById(orderId);
            Order order = JSON.parseObject(JSON.toJSONString(orderResult.getData()), Order.class);
            //设置已评论
            order.setComment(1);
            //调用更新服务
            logger.info("要更新的订单:{}",order);
            orderService.updateOrder(order);
        }
        return rs;
    }

    @Override
    public int updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

    @Override
    public List<Comment> queryByNFTId(int nftId) {

        List<Comment> comments = commentDao.queryByNFTId(nftId);
        //憨憨了属于是
//        for (Comment comment : comments) {
//            int userId = comment.getUserId();
//            HashMap<Object, Object> conditionMap = new HashMap<>();
//            conditionMap.put("id",userId);
//            CommonResult<Users> commonResult = userService.queryUsers(conditionMap);
              //也不知道这种骚操作当初我是和谁学的
//            String data = JSON.toJSONString(commonResult.getData(), SerializerFeature.WriteMapNullValue);
//            String substring = data.substring(1, data.length() - 1);
//            Users users = JSON.parseObject(substring, Users.class);
//            comment.setUsers(users);
//        }
        return comments;
    }
}
