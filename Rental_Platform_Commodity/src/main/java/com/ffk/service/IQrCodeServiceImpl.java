package com.ffk.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 房发科
 * @date 2021/2/22 21:34
 */
@Service
public class IQrCodeServiceImpl implements IQrCodeService{
    @Autowired
    private QrConfig config;

    /**
     * 创建到文件
     * @param content
     * @param filePath
     */
    @Override
    public void createCodeToFile(String content, String filePath) {
        try {
            QrCodeUtil.generate(content, config, FileUtil.file(filePath));

        } catch (QrCodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以流的形式返回
     * @param content
     * @param response
     */
    @Override
    public void createCodeToStream(String content, HttpServletResponse response) {
        try {
            QrCodeUtil.generate(content,config, "png", response.getOutputStream());
        } catch (QrCodeException | IOException e) {
            e.printStackTrace();
        }
    }
}
