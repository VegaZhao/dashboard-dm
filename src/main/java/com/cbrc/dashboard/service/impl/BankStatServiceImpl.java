package com.cbrc.dashboard.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cbrc.dashboard.dao.mapper.CreditCoporationLoanMapper;
import com.cbrc.dashboard.dao.po.CreditCorporationLoan;
import com.cbrc.dashboard.service.BankStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.service.impl
 * @author: Herry
 * @Date: 2020/9/9 14:44
 * @Description: TODO
 */
@Service
public class BankStatServiceImpl implements BankStatService {

    @Autowired
    private CreditCoporationLoanMapper creditCoporationLoanMapper;


    @Override
    public List<String> getBankNameList() {
        EntityWrapper<CreditCorporationLoan> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("DISTINCT BANKNAME");
        List<Map<String,Object>> bankNameList = creditCoporationLoanMapper.selectMaps(entityWrapper);
        List<String> result = new ArrayList<>();
        for (Map<String,Object> r : bankNameList) {
            result.add(r.get("BANKNAME").toString());
        }
        return result;
    }
}
