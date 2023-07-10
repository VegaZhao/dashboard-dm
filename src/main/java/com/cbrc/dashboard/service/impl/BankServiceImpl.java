package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.bo.AssureStatDataBo;
import com.cbrc.dashboard.bo.BankStatDataBo;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.mapper.BankTypeMapper;
import com.cbrc.dashboard.dao.po.BankType;
import com.cbrc.dashboard.service.BankService;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankTypeMapper bankTypeMapper;

    @Autowired
    private CreditCorporationLoanService creditCorporationLoanService;

    @Override
    public List<Map> getBankTypeList() {
        List<Map> list = bankTypeMapper.getBankTypeList();
        return list;
    }

    @Override
    public List<Map> getBankList(String bankType) {
        List<Map> list = bankTypeMapper.getBankList(bankType);
        return list;
    }

    @Override
    public List<BankStatDataBo> getBankStatData(String monthBegin, String monthEnd, String bankType, String bankName) {

        List<CreditCorporationLoanDto> creditCorporationLoanDtos = creditCorporationLoanService.getBankStateData(monthBegin, monthEnd, bankType, bankName);
        List<BankStatDataBo> returnList = new ArrayList<>();
        Map<String, BankStatDataBo> bankStatCustRankBoMap = new HashMap<>();
        Map<String, BankStatDataBo> bankStatGroupRankBoMap = new HashMap<>();
        Map<String, BankStatDataBo> bankLoanDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> bankAssDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> bankIndustryDataBoMap = new HashMap<>();
        Map<String, BankStatDataBo> bankBusiDataBoMap = new HashMap<>();
        for (CreditCorporationLoanDto ccld : creditCorporationLoanDtos) {
            if (bankStatCustRankBoMap.containsKey(ccld.getCustomerName())) {
                BankStatDataBo asb = bankStatCustRankBoMap.get(ccld.getCustomerName());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("customer");
                asb.setType(ccld.getSocialCreditCode());
                asb.setTypeDesc(ccld.getCustomerName());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankStatCustRankBoMap.put(asb.getTypeDesc(), asb);
            }
            if (bankStatGroupRankBoMap.containsKey(ccld.getGroupName())) {
                BankStatDataBo asb = bankStatGroupRankBoMap.get(ccld.getGroupName());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("custGroup");
                asb.setType(ccld.getGroupID());
                asb.setTypeDesc(ccld.getGroupName());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankStatGroupRankBoMap.put(asb.getTypeDesc(), asb);
            }
            if (bankLoanDataBoMap.containsKey(ccld.getLoanWay().toString())) {
                BankStatDataBo asb = bankLoanDataBoMap.get(ccld.getLoanWay().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("loanWay");
                asb.setType(ccld.getLoanWay().toString());
                asb.setTypeDesc(ccld.getLoanWayDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankLoanDataBoMap.put(asb.getType(), asb);
            }
            if (bankAssDataBoMap.containsKey(ccld.getGuaranteeType().toString())) {
                BankStatDataBo asb = bankAssDataBoMap.get(ccld.getGuaranteeType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("assure");
                asb.setType(ccld.getGuaranteeType().toString());
                asb.setTypeDesc(ccld.getGuaranteeTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankAssDataBoMap.put(asb.getType(), asb);
            }
            if (bankIndustryDataBoMap.containsKey(ccld.getVocationCode().toString())) {
                BankStatDataBo asb = bankIndustryDataBoMap.get(ccld.getVocationCode().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("industry");
                asb.setType(ccld.getVocationCode().toString());
                asb.setTypeDesc(ccld.getVocationCodeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankIndustryDataBoMap.put(asb.getType(), asb);
            }
            if (bankBusiDataBoMap.containsKey(ccld.getLoanType().toString())) {
                BankStatDataBo asb = bankBusiDataBoMap.get(ccld.getLoanType().toString());
                asb.setBalance(ccld.getIssueMoney() + asb.getBalance());
                asb.setNum(asb.getNum() + 1);
            } else {
                BankStatDataBo asb = new BankStatDataBo();
                asb.setDataDim("business");
                asb.setType(ccld.getLoanType().toString());
                asb.setTypeDesc(ccld.getLoanTypeDesc());
                asb.setBalance(ccld.getIssueMoney());
                asb.setNum(1);
                bankBusiDataBoMap.put(asb.getType(), asb);
            }
        }
        Collection<BankStatDataBo> listCustom =  bankStatCustRankBoMap.values();
        listCustom = listCustom.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).limit(25).collect(Collectors.toList());
        returnList.addAll(listCustom);
        Collection<BankStatDataBo> listGroup =  bankStatGroupRankBoMap.values();
        listGroup = listGroup.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).limit(25).collect(Collectors.toList());
        returnList.addAll(listGroup);
        Collection<BankStatDataBo> listLoan = bankLoanDataBoMap.values();
        listLoan = listLoan.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listLoan);
        Collection<BankStatDataBo> listAssure = bankAssDataBoMap.values();
        listAssure = listAssure.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listAssure);
        Collection<BankStatDataBo> listIndustry = bankIndustryDataBoMap.values();
        listIndustry = listIndustry.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listIndustry);
        Collection<BankStatDataBo> listBusiness = bankBusiDataBoMap.values();
        listBusiness = listBusiness.stream().sorted(Comparator.comparing(BankStatDataBo::getBalance).reversed()).collect(Collectors.toList());
        returnList.addAll(listBusiness);
        return returnList;
    }

    @Override
    public List<Map> getBankStateIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName) {

        List<Map> bankStateIssueMoney = creditCorporationLoanService.getBankStateIssueMoney(monthBegin, monthEnd, bankType, bankName);
        List<Map> returnList = new ArrayList<>();
        //保存不重复的银行类型，值为该银行类型的合计值
        Map<String, Map> mapBankType = new HashMap<>();
        //循环增加银行类型，如存在就不再重复添加
        for (Map map : bankStateIssueMoney) {
            String name = map.get("type_name").toString();
            if (mapBankType.containsKey(name)) {
                continue;
            } else {
                mapBankType.put(name, new HashMap());
            }
        }
        //循环累加银行类型的金额
        for (String typeName : mapBankType.keySet()) {
            Map<String, Object> mapTemp = mapBankType.get(typeName);
            mapTemp.put("name", typeName);
            mapTemp.put("children", new ArrayList<>());
            for (Map map : bankStateIssueMoney) {
                if (map.get("type_name").toString().equals(typeName)) {
                    if (mapTemp.containsKey("value")) {
                        BigDecimal valueD = (BigDecimal) mapTemp.get("value");
                        valueD = valueD.add((BigDecimal) map.get("amt"));
                        mapTemp.put("value", valueD);
                    } else {
                        mapTemp.put("value", (BigDecimal) map.get("amt"));
                        mapTemp.put("id", map.get("type_id"));
                    }
                }
            }
        }
        //将银行的数据作为children加到银行类型的数据中
        for (Map map : bankStateIssueMoney) {
            Map mBbankType = mapBankType.get(map.get("type_name"));
            List children = (List) mBbankType.get("children");
            Map<String, Object> mapBank = new HashMap<>();
            mapBank.put("name", map.get("BANKNAME").toString());
            mapBank.put("value", (BigDecimal) map.get("amt"));
            mapBank.put("id", map.get("bank_id"));
            children.add(mapBank);
        }
        for (Map map : mapBankType.values()) {
            returnList.add(map);
        }
        return returnList;
    }
}
