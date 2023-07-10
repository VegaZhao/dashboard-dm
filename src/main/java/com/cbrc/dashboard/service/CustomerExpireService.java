package com.cbrc.dashboard.service;

import com.cbrc.dashboard.bo.CustomerStatDataBo;

import java.util.List;
public interface CustomerExpireService {

    List<CustomerStatDataBo> getCustomerExpireLineData(String dateBegin, String dateEnd);

    List<CustomerStatDataBo> getCustomerExpireTableData(String dateBegin, String dateEnd);

}
