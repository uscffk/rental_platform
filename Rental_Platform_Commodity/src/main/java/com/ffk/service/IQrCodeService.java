package com.ffk.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 房发科
 * @date 2021/2/22 21:32
 */
public interface IQrCodeService {
    /**
     * 生成到文件
     * @param content
     * @param filePath
     */
    void createCodeToFile(String content, String filePath);

    /**
     * 生成到流
     * @param content
     * @param response
     */
    void createCodeToStream(String content, HttpServletResponse response);
}
