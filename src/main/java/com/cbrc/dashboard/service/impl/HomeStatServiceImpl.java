package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.dto.HomeMapDto;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.service.HomeStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class HomeStatServiceImpl implements HomeStatService {

    @Autowired
    private CreditCorporationLoanService creditCorporationLoanService;

    @Override
    public List<Map> getHomeTreeIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName) {

        List<HomeMapDto> bankStateIssueMoney = creditCorporationLoanService.getHomeTreeIssueMoney(monthBegin, monthEnd, bankType, bankName);
        List<Map> returnList = new ArrayList<>();
        //保存不重复的银行类型，值为该银行类型的合计值
        Map<String, Map> mapBankType = new HashMap<>();
        //1、循环增加银行类型，如存在就不再重复添加
        for (HomeMapDto hmd : bankStateIssueMoney) {
            if (mapBankType.containsKey(hmd.getTypeName())) {
                continue;
            } else {
                mapBankType.put(hmd.getTypeName(), new HashMap());
            }
        }
        //循环累加银行类型的金额
        for (String typeName : mapBankType.keySet()) {
            Map<String, Object> mapTemp = mapBankType.get(typeName);
            mapTemp.put("name", typeName);
            mapTemp.put("children", new ArrayList<>());
            for (HomeMapDto hmd : bankStateIssueMoney) {
                if (hmd.getTypeName().equals(typeName)) {
                    if (mapTemp.containsKey("value")) {
                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
                        valueD = valueD.add(BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("value", valueD);
                    } else {
                        mapTemp.put("value", BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("id", hmd.getTypeId());
                    }
                }
            }
        }
        Map<String, Map> mapBank = new HashMap<>();
        //2、循环增加银行名称，如存在就不再重复添加
        for (HomeMapDto hmd : bankStateIssueMoney) {
            if (mapBank.containsKey(hmd.getBankName())) {
                continue;
            } else {
                mapBank.put(hmd.getBankName(), new HashMap());
            }
        }
        //循环累加各银行的金额
        for (String banksName : mapBank.keySet()) {
            Map<String, Object> mapTemp = mapBank.get(banksName);
            mapTemp.put("name", banksName);
            mapTemp.put("children", new ArrayList<>());
            for (HomeMapDto hmd : bankStateIssueMoney) {
                if (hmd.getBankName().equals(banksName)) {
                    if (mapTemp.containsKey("value")) {
                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
                        valueD = valueD.add(BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("value", valueD);
                    } else {
                        mapTemp.put("value", BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("id", hmd.getBankId());
                    }
                }
            }
        }
        //将银行的数据作为children加到银行类型的数据中
        Map<String, Map> mapTypeChild = new HashMap<>();
        for (HomeMapDto hmd : bankStateIssueMoney) {
            Map mBbankType = mapBankType.get(hmd.getTypeName());
            if (mapTypeChild.containsKey(hmd.getBankName())) {
                continue;
            } else {
                mapTypeChild.put(hmd.getBankName(), new HashMap());
                List children = (List) mBbankType.get("children");
                children.add(mapBank.get(hmd.getBankName()));
            }
        }
        Map<String, Map> mapCustomer = new HashMap<>();
        //3、循环增加客户，如存在就不再重复添加
        for (HomeMapDto hmd : bankStateIssueMoney) {
            if (mapCustomer.containsKey(hmd.getCustomerName())) {
                continue;
            } else {
                mapCustomer.put(hmd.getCustomerName(), new HashMap());
            }
        }
        //循环累加客户的金额
        for (String customerName : mapCustomer.keySet()) {
            Map<String, Object> mapTemp = mapCustomer.get(customerName);
            mapTemp.put("name", customerName);
            mapTemp.put("children", new ArrayList<>());
            for (HomeMapDto hmd : bankStateIssueMoney) {
                if (hmd.getCustomerName().equals(customerName)) {
                    if (mapTemp.containsKey("value")) {
                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
                        valueD = valueD.add(BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("value", valueD);
                    } else {
                        mapTemp.put("value", BigDecimal.valueOf(hmd.getIssueMoney()));
                        mapTemp.put("id", hmd.getSocialCreditCode());
                    }
                }
            }
        }
        //将客户的数据作为children加到各银行的数据中
        Map<String, Map> mapBankChild = new HashMap<>();
        for (HomeMapDto hmd : bankStateIssueMoney) {
            Map mBank = mapBank.get(hmd.getBankName());
            if (mapBankChild.containsKey(hmd.getCustomerName())) {
                continue;
            } else {
                mapBankChild.put(hmd.getCustomerName(), new HashMap());
                List children = (List) mBank.get("children");
                children.add(mapCustomer.get(hmd.getCustomerName()));
            }
        }
        //将明细数据作为children加到各客户下
        for (HomeMapDto hmd : bankStateIssueMoney) {
            Map mCustomer = mapCustomer.get(hmd.getCustomerName());
            List children = (List) mCustomer.get("children");
            Map<String, Object> mapDetail = new HashMap<>();
            mapDetail.put("name", hmd.getIssueMoney());
            mapDetail.put("value", hmd.getIssueMoney());
            children.add(mapDetail);
        }

        for (Map map : mapBankType.values()) {
            returnList.add(map);
        }
        return returnList;
    }
}
