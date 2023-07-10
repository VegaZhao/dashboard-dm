package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.bo.CustomerStatDataBo;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.service.CustomerExpireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerExpireImpl implements CustomerExpireService {

    @Autowired
    private CreditCorporationLoanService creditCorporationLoanService;

    @Override
    public List<CustomerStatDataBo> getCustomerExpireLineData(String dateBegin, String dateEnd) {

        List<CreditCorporationLoanDto> creditCorporationLoanDtos = creditCorporationLoanService.getCreditCorporationLoanByData(dateBegin,dateEnd);
        List<CustomerStatDataBo> returnList = new ArrayList<>();
        Map<String,CustomerStatDataBo> customerStatDataBoMap = new HashMap<>();
        for (CreditCorporationLoanDto ccld : creditCorporationLoanDtos) {
            if(customerStatDataBoMap.containsKey(ccld.getDueDate())) {
                CustomerStatDataBo asb = customerStatDataBoMap.get(ccld.getDueDate());
                asb.setLoanBalance(ccld.getIssueMoney() + asb.getLoanBalance());
                asb.setLoanNum(asb.getLoanNum() + 1);
            }else{
                CustomerStatDataBo asb = new CustomerStatDataBo();
                asb.setDueDate(ccld.getDueDate());
                asb.setLoanBalance(ccld.getIssueMoney());
                asb.setLoanNum(1);
                customerStatDataBoMap.put(asb.getDueDate(),asb);
            }
        }
//        return new ArrayList<>(customerStatDataBoMap.values());

        Collection<CustomerStatDataBo> list =  customerStatDataBoMap.values();
        list = list.stream().sorted(Comparator.comparing(CustomerStatDataBo::getDueDate)).collect(Collectors.toList());
        returnList.addAll(list);
        return returnList;
    }

    //不用了
    @Override
    public List<CustomerStatDataBo> getCustomerExpireTableData(String dateBegin, String dateEnd) {

        List<CreditCorporationLoanDto> creditCorporationLoanDtos = creditCorporationLoanService.getCreditCorporationLoanByData(dateBegin,dateEnd);
        ArrayList<CustomerStatDataBo> customerStatDataBoMap = new ArrayList<>();
        for (CreditCorporationLoanDto ccld : creditCorporationLoanDtos) {
            CustomerStatDataBo asb = new CustomerStatDataBo();
            asb.setCustomerName(ccld.getCustomerName());
            asb.setStartDate(ccld.getStartDate());
            asb.setDueDate(ccld.getDueDate());
            asb.setIssueMoney(ccld.getIssueMoney());
            customerStatDataBoMap.add(asb);
        }

        return new ArrayList<>(customerStatDataBoMap);
    }

}
