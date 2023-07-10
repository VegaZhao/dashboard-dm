package com.cbrc.dashboard.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cbrc.dashboard.excel.exception.ExcelException;
import com.cbrc.dashboard.excel.listen.ExcelListener;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelUtil
 * 基于easyExcel的开源框架，poi版本3.17
 * BeanCopy ExcelException 属于自定义数据，属于可自定义依赖
 * 工具类尽可能还是需要减少对其他java的包的依赖
 * @author wenxuan.wang
 */
public class ExcelUtil {
    /**
     * 私有化构造方法
     */
    private ExcelUtil(){}

    /**
     * 读取 Excel(多个 sheet)
     * 将多sheet合并成一个list数据集，通过自定义ExcelReader继承AnalysisEventListener
     * 重写invoke doAfterAllAnalysed方法
     * getExtendsBeanList 主要是做Bean的属性拷贝 ，可以通过ExcelReader中添加的数据集直接获取
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static <T> Map<String,List<T>> readExcel(MultipartFile excel,Class<T>  rowModel) throws IOException {
        InputStream io = excel.getInputStream();
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(io, rowModel, excelListener).doReadAll();
        return getExtendsBeanMap(excelListener.getData(),rowModel);
    }

    /**
     * 读取 指定范围内Excel(多个 sheet)
     * 将多sheet合并成一个list数据集，通过自定义ExcelReader继承AnalysisEventListener
     * 重写invoke doAfterAllAnalysed方法
     * getExtendsBeanList 主要是做Bean的属性拷贝 ，可以通过ExcelReader中添加的数据集直接获取
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param headLineNum 表头行数，默认为1
     * @param start  开始位置
     * @param end 结束位置
     * @return Excel 数据 list
     */
    public static <T> Map<String,List<T>> readExcel(MultipartFile excel, Class<T>  rowModel, int headLineNum, int start, int end,Map<String,Object> replace) throws ExcelException {
        ExcelListener excelListener = new ExcelListener(replace);
        ExcelReader reader = getReader(excel);
        if (reader == null) {
            return new HashMap<>();
        }
        Map<String,List<T>> result = new HashMap<>();
        List<ReadSheet> sheets = new ArrayList<>();
        for (int i = start ; i <= end; i++ ) {
            ReadSheet sheet = EasyExcel.readSheet(i).head(rowModel).registerReadListener(excelListener).build();
            sheet.setHeadRowNumber(headLineNum);
            sheets.add(sheet);
        }
        reader.read(sheets);
        reader.finish();

        result = getExtendsBeanMap(excelListener.getData(),rowModel);
        return result;
    }


    /**
     * 读取 指定sheet名称的Excel(多个 sheet)
     * 将多sheet合并成一个list数据集，通过自定义ExcelReader继承AnalysisEventListener
     * 重写invoke doAfterAllAnalysed方法
     * @param excel
     * @param headLineNum
     * @param sheetType 每个sheet页的名称以及该sheet页模型类型
     * @return
     * @throws ExcelException
     */
    public static  Map<String,List<Object>> readExcel(MultipartFile excel, int headLineNum, Map<String,Class> sheetType) throws ExcelException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel);
        if (reader == null) {
            return new HashMap<>();
        }

        Map<String,List<Object>> result = new HashMap<>();
        List<ReadSheet> sheets = new ArrayList<>();
        for (Map.Entry<String,Class> entry : sheetType.entrySet()) {
            ReadSheet sheet = EasyExcel.readSheet(entry.getKey()).head(entry.getValue()).registerReadListener(excelListener).build();
            sheet.setHeadRowNumber(headLineNum);
            sheets.add(sheet);
        }
        reader.read(sheets);
        reader.finish();

        result = excelListener.getData();
        return result;
    }


    /**
     * 读取某个 sheet 的 Excel
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static <T> Map<String,List<T>> readExcel(MultipartFile excel, Class<T>  rowModel, int sheetNo)  throws ExcelException{
        return readExcel(excel, rowModel, sheetNo, 1,null);
    }

    /**
     * 读取某个 sheet 的 Excel,根据替换数组内的值对读取内容进行替换
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @param replace 替换数组
     * @return Excel 数据 list
     */
    public static <T> Map<String,List<T>> readExcel(MultipartFile excel, Class<T>  rowModel, int sheetNo,Map<String,Object> replace)  throws ExcelException{
        return readExcel(excel, rowModel, sheetNo, 1,replace);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @param replace 替换数组
     * @return Excel 数据 list
     */
    public static <T> Map<String,List<T>> readExcel(MultipartFile excel, Class<T>  rowModel, int sheetNo,
                                         int headLineNum, Map<String,Object> replace) throws ExcelException {
        ExcelListener excelListener = new ExcelListener(replace);
        ExcelReader reader = getReader(excel);
        if (reader == null) {
            return new HashMap<>();
        }
        ReadSheet sheet = EasyExcel.readSheet(sheetNo).head(rowModel).registerReadListener(excelListener).build();
        sheet.setHeadRowNumber(headLineNum);
        reader.read(sheet);
        reader.finish();
        return getExtendsBeanMap(excelListener.getData(),rowModel);
    }

    /**
     * 导出 Excel ：一个 sheet，带表头
     * 自定义WriterHandler 可以定制行列数据进行灵活化操作
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     */
    public static <T extends BaseRowModel> void writeExcel(HttpServletResponse response, List<T> list,
                                  String fileName, String sheetName, ExcelTypeEnum excelTypeEnum,
                                  Class<T> classType)  throws ExcelException{
        if(sheetName == null || "".equals(sheetName)){
            sheetName = "sheet1";
        }
        if(excelTypeEnum == ExcelTypeEnum.XLSX) {
            ExcelWriter writer = EasyExcelFactory.getWriterWithTempAndHandler(null,
                    getOutputStream(fileName, response, excelTypeEnum), excelTypeEnum, true, new WriterHandler07<>(classType));
            Sheet sheet = new Sheet(1, 0, classType);
            sheet.setSheetName(sheetName);
            try {
                writer.write(list, sheet);
            } finally {
                writer.finish();
            }
            //其实也可以专门调03版的样式，或者直接套用
        } else if(excelTypeEnum == ExcelTypeEnum.XLS ){
            ExcelWriterFactory writer = new ExcelWriterFactory(getOutputStream(fileName, response,excelTypeEnum), excelTypeEnum);
            Sheet sheet = new Sheet(1, 0, classType);
            sheet.setSheetName(sheetName);
            try {
                writer.write(list, sheet);
            } finally {
                writer.finish();
                writer.close();
            }
        }

    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactory writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object, ExcelTypeEnum excelTypeEnum) throws ExcelException {
        ExcelWriterFactory writer = new ExcelWriterFactory(getOutputStream(fileName, response,excelTypeEnum), excelTypeEnum);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response, ExcelTypeEnum excelTypeEnum) throws ExcelException{
        //创建本地文件
        String filePath = fileName + excelTypeEnum.getValue();
        try {
            fileName = new String(filePath.getBytes(), StandardCharsets.ISO_8859_1);
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("创建文件失败！");
        }
    }

    /**
     * 返回 ExcelReader
     * @param excel         需要解析的 Excel 文件
     */
    private static ExcelReader getReader(MultipartFile excel) throws ExcelException{
        String fileName = excel.getOriginalFilename();
        if (fileName == null ) {
            throw new ExcelException("文件格式错误！");
        }
        if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
            throw new ExcelException("文件格式错误！");
        }
        InputStream inputStream;
        try {
            inputStream = excel.getInputStream();
            return EasyExcel.read(inputStream).build();
        } catch (IOException e) {
            //do something
        }
        return null;
    }

    /**
     * 利用BeanCopy转换list
     */
    public static <T> List<T> getExtendsBeanList(List<Object> list,Class<T> typeClazz){
        return MyBeanCopy.convert(list,typeClazz);
    }

    public static <T> Map<String,List<T>> getExtendsBeanMap(Map<String,List<Object>> map,Class<T> typeClazz){
        return MyBeanCopy.convert(map,typeClazz);
    }

    public static <T> List<T> getOneSheetValue(Map<String,List<T>> map) {
        List<T> result = new ArrayList<>();
        if(map.size() == 1) {
            for(String key : map.keySet()) {
                result = map.get(key);
                break;
            }
        }
        return result;
    }
}
