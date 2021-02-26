package com.ffk.controller;

import com.ffk.constant.Constants;
import com.ffk.pojo.CommonResult;
import com.ffk.service.IQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
/**
 * @author 房发科
 * @date 2021/2/22 21:48
 */
@RestController
public class QrCodeController {
    @Autowired
    private IQrCodeService codeService;

    /**
     * 生成商品的二维码
     * @param content  一个存有商品信息的字符串
     * @param servletResponse
     * @return
     */
    @GetMapping("qrCode")
    public CommonResult generateV3(String content, HttpServletResponse servletResponse){
        CommonResult response=new CommonResult(Constants.SUCCESS_CODE,"成功",null);
        try {
            //将生成的二维码文件存放于文件目录中
            //final String fileName=LOCALDATEFORMAT.get().format(new Date());
            //codeService.createCodeToFile(content,RootPath+ File.separator+fileName+".png");

            //将生成的二维码文件直接返回给前端响应流
            codeService.createCodeToStream(content,servletResponse);
        }catch (Exception e){
            response=new CommonResult(Constants.FAIL_CODE,"失败",null);
        }
        return response;
    }
}
