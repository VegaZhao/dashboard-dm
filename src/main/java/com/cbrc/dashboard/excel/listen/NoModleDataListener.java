package com.cbrc.dashboard.excel.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.excel.listen
 * @author: Herry
 * @Date: 2020/3/15 15:28
 * @Description: TODO
 */
public class NoModleDataListener extends AnalysisEventListener<Map<Integer,String>> {

    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
