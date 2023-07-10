package com.cbrc.dashboard.service;

import java.util.List;
import java.util.Map;

public interface HomeStatService {

    List<Map> getHomeTreeIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName);
}
