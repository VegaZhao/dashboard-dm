package com.cbrc.dashboard.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.cbrc.dashboard.excel.listen.ExcelListener;
import com.cbrc.dashboard.utils.FileUtil;
import com.cbrc.dashboard.utils.LoggerUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @author: Herry
 * @Date: 2019/3/1712:17
 * @Description: TODO
 */
public class ExcelRead {

    /**
     * @param fileName    文件名称
     * @param sheetNo     excel sheet 从1开始
     * @param headlineMun 所选excel中起始行
     * @param clazz       模型类
     * @return
     * @throws Exception
     */
    public static List<Object> readExcelWithModel(String fileName, int sheetNo, int headlineMun, Class<? extends BaseRowModel> clazz) throws Exception {
        InputStream in = FileUtil.getResourcesFileInputStream(fileName);
        ExcelListener excelListener = new ExcelListener();
        try {
            EasyExcelFactory.readBySax(in, new Sheet(sheetNo, headlineMun, clazz), excelListener);
        } catch (Exception e) {
            LoggerUtils.error(ExcelRead.class, "读取Excel[" + fileName + "]失败!");
            e.printStackTrace();
        } finally {
            in.close();
        }

        List<Object> result = null;
        Map<String,List<Object>> data = excelListener.getData();
        if(data.size() == 1) {
            for (String key : data.keySet()){
                result  = data.get(key);
                break;
            }
        }
        return result;
    }
}
