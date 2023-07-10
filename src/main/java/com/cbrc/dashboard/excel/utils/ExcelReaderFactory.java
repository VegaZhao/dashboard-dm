package com.cbrc.dashboard.excel.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @author: Herry
 * @Date: 2019/3/1711:08
 * @Description: TODO
 */
public class ExcelReaderFactory {
    /**
     * @param in            文件输入流
     * @param customContent 自定义模型可以在
     *                      {@link AnalysisEventListener#invoke(Object, AnalysisContext) }
     *                      AnalysisContext中获取用于监听者回调使用
     * @param eventListener 用户监听
     * @throws IOException
     * @throws EmptyFileException
     * @throws InvalidFormatException
     */
    public static ExcelReader getExcelReader(InputStream in, Object customContent,
                                             AnalysisEventListener<?> eventListener) throws EmptyFileException, IOException, InvalidFormatException {
        // 确保至少有一些数据
        ExcelTypeEnum excelTypeEnum = null;
        if (FileMagic.valueOf(in) == FileMagic.OLE2) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (FileMagic.valueOf(in) == FileMagic.OOXML) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");

    }

    /**
     * @param in            文件输入流
     * @param customContent 自定义模型可以在
     *                      {@link AnalysisEventListener#invoke(Object, AnalysisContext) }
     *                      AnalysisContext中获取用于监听者回调使用
     * @param eventListener 用户监听
     * @param trim          是否对解析的String做trim()默认true,用于防止 excel中空格引起的装换报错。
     * @throws IOException
     * @throws EmptyFileException
     * @throws InvalidFormatException
     */
    public static ExcelReader getExcelReader(InputStream in, Object customContent,
                                             AnalysisEventListener<?> eventListener, boolean trim)
            throws EmptyFileException, IOException, InvalidFormatException {
        ExcelTypeEnum excelTypeEnum = null;
        if (FileMagic.valueOf(in) == FileMagic.OLE2) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        }
        if (FileMagic.valueOf(in) == FileMagic.OOXML) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        }
        if (excelTypeEnum != null) {
            return new ExcelReader(in, excelTypeEnum, customContent, eventListener, trim);
        }
        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }
}
