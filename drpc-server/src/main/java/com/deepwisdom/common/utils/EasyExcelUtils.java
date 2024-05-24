package com.deepwisdom.common.utils;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author maobo
 */
public class EasyExcelUtils {

    /**
     * EasyExcel web 文件下载
     *
     * @param fileName 导出文件名
     * @param list  结果数据集合
     * @param head 映射class
     * @param response  responsed对象
     * @return {@link R}
     */
    public static void download(String fileName, List list , Class head , HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode防止中文乱码,%20=空格
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename*=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), head).sheet("Sheet1").doWrite(list);
    }
}
