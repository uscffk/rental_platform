package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.Commodity;
import com.ffk.pojo.CommonResult;
import com.ffk.service.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author 房发科
 * @date 2021/3/5 22:19
 */
@RestController
public class FileUploadController {

    public final static String IMG_PATH_PREFIX = "Rental_Platform_Commodity/src/main/resources/static/upload/imgs";

    @Autowired
    private ICommodityService commodityService;

    /**
     * 上传图片描述
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadCommodityPic",method = RequestMethod.POST)
    public CommonResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）

        //上传路径保存设置
        String path = IMG_PATH_PREFIX;

        //获取文件上传携带过来的参数username
        String commodityId = request.getParameter("commodityId");
        System.out.println("1111"+commodityId);
        File realPath = new File(path);
        //如果不存在则创建文件
        if (!realPath.exists()) {
            System.out.println(1);
            realPath.mkdir();
        }
        System.out.println(commodityId);
        try{
            //保存文件到服务器 以系统时间作为文件名
            long time= System.currentTimeMillis();
            File headPath = new File(realPath + "/" + time+".jpg");
            System.out.println(headPath.getAbsolutePath());
            file.transferTo(headPath);
            System.out.println(commodityId);
            //保存到数据库 将图片的url保存到数据库
//            Commodity commodity = new Commodity();
            //查询商品
            Commodity commodity = commodityService.queryById(Integer.parseInt(commodityId));

//            commodity.setId(Integer.parseInt(commodityId));
            commodity.setPicture(time+".jpg");
            int rs = commodityService.updateCommodity(commodity);
            if(rs!=0){
                return new CommonResult(Constants.SUCCESS_CODE,"ok",time+".jpg");
            }else {
                return new CommonResult(Constants.FAIL_CODE,"error",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(Constants.FAIL_CODE,"error",null);
        }
    }
}
