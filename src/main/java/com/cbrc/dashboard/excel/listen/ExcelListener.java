package com.cbrc.dashboard.excel.listen;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cbrc.dashboard.excel.utils.MyStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelListener extends AnalysisEventListener {

    private Map<String,List<Object>> data = new HashMap<>();

    private Map<String,Object> replaceMap = null;

    public ExcelListener(Map<String,Object> replaceMap) {
        super();
        this.replaceMap = replaceMap;
    }

    public ExcelListener() {
        super();
    }

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        String sheetName = context.readSheetHolder().getSheetName().trim();
        if(!checkObjAllFieldsIsNull(object)) {
            if(!data.containsKey(sheetName)) {
                data.put(sheetName,new ArrayList<>());
            }
            if(replaceMap != null) {
                replace(object,replaceMap);
            }
            data.get(sheetName).add(object);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //do something
    }

    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    private static void replace(Object object,Map<String,Object> replaceMap) {
        if(object != null) {
            try {
                for (Field f : object.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    //只校验带ExcelProperty注解的属性
                    ExcelProperty property = f.getAnnotation(ExcelProperty.class);
                    if(property == null || SERIAL_VERSION_UID.equals(f.getName())){
                        continue;
                    }
                    if (f.get(object) instanceof String && replaceMap.containsKey(f.get(object).toString().trim())) {
                        f.set(object,replaceMap.get(f.get(object).toString().trim()));
                    }
                }
            } catch (Exception e) {
                //do something
            }
        }
    }

    /**
     * 判断对象中属性值是否全为空
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                //只校验带ExcelProperty注解的属性
                ExcelProperty property = f.getAnnotation(ExcelProperty.class);
                if(property == null || SERIAL_VERSION_UID.equals(f.getName())){
                    continue;
                }
                if (f.get(object) != null && MyStringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            //do something
        }
        return true;
    }

    public Map<String,List<Object>> getData() {
        return data;
    }
}