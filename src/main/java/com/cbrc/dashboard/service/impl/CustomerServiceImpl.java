package com.cbrc.dashboard.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.bo.BankStatDataBo;
import com.cbrc.dashboard.bo.CustomerStatDataBo;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.mapper.CustTypeMapper;
import com.cbrc.dashboard.dao.po.GroupType;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustTypeMapper custTypeMapper;

    @Autowired
    private CreditCorporationLoanService creditCorporationLoanService;

    @Override
    public List<Map> getGroupList(String monthBegin, String monthEnd) {
        List<Map> list = custTypeMapper.getGroupList(monthBegin, monthEnd);
        return list;
    }

    @Override
    public List<Map> getCustList(String monthBegin, String monthEnd, String custName, Integer num) {
        List<Map> list = custTypeMapper.getCustList(monthBegin, monthEnd, custName, num);
        return list;
    }

    //集团选择器获取
    @Override
    public List<GroupType> getGroupCustList(String monthBegin, String monthEnd, String groupName, Integer num) {
        List<GroupType> list = custTypeMapper.getGroupCustList(monthBegin, monthEnd, groupName, num);
        return list;
    }

    @Override
    public List<BankStatDataBo> getCustStateData(String monthBegin, String monthEnd, String custName) {

        List<CreditCorporationLoanDto> creditCorporationLoanDtos = creditCorporationLoanService.getCustStateData(monthBegin, monthEnd, custName);
        List<BankStatDataBo> returnList = new ArrayList<>();
        Map<String, BankStatDataBo> custLoanDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custAssDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custIndustryDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custBusiDataBoMap = new HashMap<>();
        for (CreditCorporationLoanDto ccld : creditCorporationLoanDtos) {
            if (custLoanDataBoMap.containsKey(ccld.getLoanWay().toString())) {
                BankStatDataBo asb = custLoanDataBoMap.get(ccld.getLoanWay().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("loanWay");
                asb.setType(ccld.getLoanWay().toString());
                asb.setTypeDesc(ccld.getLoanWayDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custLoanDataBoMap.put(asb.getType(), asb);
            }
            if (custAssDataBoMap.containsKey(ccld.getGuaranteeType().toString())) {
                BankStatDataBo asb = custAssDataBoMap.get(ccld.getGuaranteeType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("assure");
                asb.setType(ccld.getGuaranteeType().toString());
                asb.setTypeDesc(ccld.getGuaranteeTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custAssDataBoMap.put(asb.getType(), asb);
            }
            if (custIndustryDataBoMap.containsKey(ccld.getVocationCode().toString())) {
                BankStatDataBo asb = custIndustryDataBoMap.get(ccld.getVocationCode().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("industry");
                asb.setType(ccld.getVocationCode().toString());
                asb.setTypeDesc(ccld.getVocationCodeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custIndustryDataBoMap.put(asb.getType(), asb);
            }
            if (custBusiDataBoMap.containsKey(ccld.getLoanType().toString())) {
                BankStatDataBo asb = custBusiDataBoMap.get(ccld.getLoanType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("business");
                asb.setType(ccld.getLoanType().toString());
                asb.setTypeDesc(ccld.getLoanTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custBusiDataBoMap.put(asb.getType(), asb);
            }
        }
        Collection<BankStatDataBo> listLoan =  custLoanDataBoMap.values();
        listLoan = listLoan.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listLoan);
        Collection<BankStatDataBo> listAssure =  custAssDataBoMap.values();
        listAssure = listAssure.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listAssure);
        Collection<BankStatDataBo> listIndustry =  custIndustryDataBoMap.values();
        listIndustry = listIndustry.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listIndustry);
        Collection<BankStatDataBo> listBusiness =  custBusiDataBoMap.values();
        listBusiness = listBusiness.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listBusiness);
        return returnList;
    }

    @Override
    public List<BankStatDataBo> getGroupStateData(String monthBegin, String monthEnd, String custName) {

        List<CreditCorporationLoanDto> creditCorporationLoanDtos = creditCorporationLoanService.getGroupStateData(monthBegin, monthEnd, custName);
        List<BankStatDataBo> returnList = new ArrayList<>();
        Map<String, BankStatDataBo> custLoanDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custAssDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custIndustryDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> custBusiDataBoMap = new HashMap<>();
        for (CreditCorporationLoanDto ccld : creditCorporationLoanDtos) {
            if (custLoanDataBoMap.containsKey(ccld.getLoanWay().toString())) {
                BankStatDataBo asb = custLoanDataBoMap.get(ccld.getLoanWay().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("loanWay");
                asb.setType(ccld.getLoanWay().toString());
                asb.setTypeDesc(ccld.getLoanWayDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custLoanDataBoMap.put(asb.getType(), asb);
            }
            if (custAssDataBoMap.containsKey(ccld.getGuaranteeType().toString())) {
                BankStatDataBo asb = custAssDataBoMap.get(ccld.getGuaranteeType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("assure");
                asb.setType(ccld.getGuaranteeType().toString());
                asb.setTypeDesc(ccld.getGuaranteeTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custAssDataBoMap.put(asb.getType(), asb);
            }
            if (custIndustryDataBoMap.containsKey(ccld.getVocationCode().toString())) {
                BankStatDataBo asb = custIndustryDataBoMap.get(ccld.getVocationCode().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("industry");
                asb.setType(ccld.getVocationCode().toString());
                asb.setTypeDesc(ccld.getVocationCodeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custIndustryDataBoMap.put(asb.getType(), asb);
            }
            if (custBusiDataBoMap.containsKey(ccld.getLoanType().toString())) {
                BankStatDataBo asb = custBusiDataBoMap.get(ccld.getLoanType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("business");
                asb.setType(ccld.getLoanType().toString());
                asb.setTypeDesc(ccld.getLoanTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                custBusiDataBoMap.put(asb.getType(), asb);
            }
        }
        Collection<BankStatDataBo> listLoan =  custLoanDataBoMap.values();
        listLoan = listLoan.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listLoan);
        Collection<BankStatDataBo> listAssure =  custAssDataBoMap.values();
        listAssure = listAssure.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listAssure);
        Collection<BankStatDataBo> listIndustry =  custIndustryDataBoMap.values();
        listIndustry = listIndustry.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listIndustry);
        Collection<BankStatDataBo> listBusiness =  custBusiDataBoMap.values();
        listBusiness = listBusiness.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listBusiness);
        return returnList;
    }

    @Override
    public List<Map> getCustStateIssueMoney(String monthBegin, String monthEnd, String custName) {
        List<Map> custStateIssueMoney = creditCorporationLoanService.getCustStateIssueMoney(monthBegin, monthEnd, custName);
        List<Map> returnList = new ArrayList<>();

        for (Map map : custStateIssueMoney) {
            Map<String, Object> mapCust = new HashMap<>();
            mapCust.put("name", map.get("CUSTOMERNAME").toString());
            mapCust.put("value", (BigDecimal) map.get("amt"));
            returnList.add(mapCust);
        }

        return returnList;
    }


    //集团树状图
    @Override
    public List<Map> getGroupStateIssueMoney(String monthBegin, String monthEnd, String custName) {
        List<Map> custStateIssueMoney = creditCorporationLoanService.getGroupStateIssueMoney(monthBegin, monthEnd, custName);
        List<Map> returnList = new ArrayList<>();

        for (Map map : custStateIssueMoney) {
            Map<String, Object> mapCust = new HashMap<>();
            mapCust.put("name", map.get("CUSTOMERNAME").toString());
            mapCust.put("value", (BigDecimal) map.get("amt"));
            returnList.add(mapCust);
        }

        return returnList;
    }

    //集团树状图-2
    @Override
    public List<Map> getGroupCustStateIssueMoney(String monthBegin, String monthEnd, String custName) {

        List<Map> custStateIssueMoney = creditCorporationLoanService.getGroupCustStateIssueMoney(monthBegin, monthEnd, custName);
        List<Map> returnList = new ArrayList<>();
        //保存不重复的集团，值为该集团的合计值
        Map<String, Map> mapGroup = new HashMap<>();
        //循环增加集团，如存在就不再重复添加
        for (Map map : custStateIssueMoney) {
            String name = map.get("CUSTOMERNAME").toString();
            if (mapGroup.containsKey(name)) {
                continue;
            } else {
                mapGroup.put(name, new HashMap());
            }
        }
        //循环累加集团的金额
        for (String typeName : mapGroup.keySet()) {
            Map<String, Object> mapTemp = mapGroup.get(typeName);
            mapTemp.put("name", typeName);
            mapTemp.put("children", new ArrayList<>());
            for (Map map : custStateIssueMoney) {
                if (map.get("CUSTOMERNAME").toString().equals(typeName)) {
                    if (mapTemp.containsKey("value")) {
                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
                        valueD = valueD.add((BigDecimal) map.get("amt"));
                        mapTemp.put("value", valueD);
                    } else {
                        mapTemp.put("value", (BigDecimal) map.get("amt"));
                    }
                }
            }
        }
        //将公司的数据作为children加到集团的数据中
        for (Map map : custStateIssueMoney) {
            Map mBbankType = mapGroup.get(map.get("CUSTOMERNAME"));
            List children = (List) mBbankType.get("children");
            Map<String, Object> mapBank = new HashMap<>();
            mapBank.put("name", map.get("MEMBERNAME").toString());
            mapBank.put("value", (BigDecimal) map.get("amt"));
            children.add(mapBank);
        }
        for (Map map : mapGroup.values()) {
            returnList.add(map);
        }
        return returnList;
    }

//    @Override
//    public List<Map> getCustStateIssueMoney(String monthBegin, String monthEnd, String groupName, String custName) {
//        List<Map> custStateIssueMoney = creditCorporationLoanService.getCustStateIssueMoney(monthBegin, monthEnd, groupName, custName);
//        List<Map> returnList = new ArrayList<>();
//        //保存不重复的集团类型，值为该集团类型的合计值
//        Map<String, Map> mapGroup = new HashMap<>();
//        //循环增加集团类型，如存在就不再重复添加
//        for (Map map : custStateIssueMoney) {
//            String name = map.get("groupName").toString();
//            if (mapGroup.containsKey(name)) {
//                continue;
//            } else {
//                mapGroup.put(name, new HashMap());
//            }
//        }
//        //循环累加集团类型的金额
//        for (String typeName : mapGroup.keySet()) {
//            Map<String, Object> mapTemp = mapGroup.get(typeName);
//            mapTemp.put("name", typeName);
//            mapTemp.put("children", new ArrayList<>());
//            for (Map map : custStateIssueMoney) {
//                if (map.get("groupName").toString().equals(typeName)) {
//                    if (mapTemp.containsKey("value")) {
//                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
//                        valueD = valueD.add((BigDecimal) map.get("amt"));
//                        mapTemp.put("value", valueD);
//                    } else {
//                        mapTemp.put("value", (BigDecimal) map.get("amt"));
//                    }
//                }
//            }
//        }
//        //将公司的数据作为children加到集团类型的数据中
//        for (Map map : custStateIssueMoney) {
//            Map mGroup = mapGroup.get(map.get("groupName"));
//            List children = (List) mGroup.get("children");
//            Map<String, Object> mapCust = new HashMap<>();
//            mapCust.put("name", map.get("CUSTOMERNAME").toString());
//            mapCust.put("value", (BigDecimal) map.get("amt"));
//            children.add(mapCust);
//        }
//        for (Map map : mapGroup.values()) {
//            returnList.add(map);
//        }
//        return returnList;
//    }

}
