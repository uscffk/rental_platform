package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.pojo.FeedBack;
import com.ffk.pojo.Users;
import com.ffk.service.FeedBackService;
import com.ffk.service.UsersService;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author 房发科
 * @date 2021/3/25 22:53
 */
@RestController

public class FileUploadController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FeedBackService feedBackService;

    Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public CommonResult uploadProtrait(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {

            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()){
                path = new File("");
            }
            //如果上传目录为/static/images/upload/,则可以如下获取
            File upload=new File("Rental_Platform_UserService/src/main/resources/static/upload/feedback/");
            if(!upload.exists()) {
                logger.info("目录不存在");
                upload.mkdirs();
                System.out.println(upload.getAbsolutePath());
            }
            try {
                //保存文件到服务器 以系统时间作为文件名
                long time= System.currentTimeMillis();
                logger.info("绝对路径为:{}",upload.getAbsolutePath());
                File headPath = new File(upload.getAbsolutePath()  + time+".jpg");

                file.transferTo(headPath);
                //保存到数据库
                FeedBack feedBack = new FeedBack();
                feedBack.setPicture("feedback"+time+".jpg");
                System.out.println(feedBack.getPicture());
                //插入数据
                feedBackService.addFeedBack(feedBack);
                return new CommonResult(Constants.SUCCESS_CODE,"ok",feedBack.getPicture());

            }catch (Exception e){
                e.printStackTrace();
            }
//        }
//        else {
//            //代表更新头像
//            //获取用户id
//            String userId = request.getParameter("userId");
//            //头像上传
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            if(!path.exists()){
//                path = new File("");
//            }
//            //如果上传目录为/static/images/upload/,则可以如下获取
//            File upload=new File(path.getAbsolutePath(),"static/upload/userhead");
//            if(!upload.exists()) {
//                upload.mkdirs();
//                System.out.println(upload.getAbsolutePath());
//            }
//            try {
//                //保存文件到服务器 以系统时间作为文件名
//                long time= System.currentTimeMillis();
//                File headPath = new File(upload.getAbsolutePath() + "/" + time+".jpg");
//                file.transferTo(headPath);
//                //保存到数据库 将图片的url保存到数据库
//                Users users = new Users();
//                users.setId(Integer.parseInt(userId));
//                users.setUserhead(time+".jpg");
//                int rs = usersService.updateUser(users);
//                if(rs!=0){
//                    return new CommonResult(Constants.SUCCESS_CODE,"ok",null);
//                }else {
//                    return new CommonResult(Constants.FAIL_CODE,"error",null);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//                return new CommonResult(Constants.FAIL_CODE,"error",null);
//            }
//
//        }
        return new CommonResult(Constants.FAIL_CODE,"error",null);
    }
}
