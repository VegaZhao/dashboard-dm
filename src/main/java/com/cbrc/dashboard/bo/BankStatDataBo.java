package com.cbrc.dashboard.bo;

import lombok.Data;

@Data
public class BankStatDataBo {
    private String Type; // 类型

    private String TypeDesc; // 类型描述

    private Integer Num; // 比数合计

    private Double Balance; // 金额合计

    private String DataDim; // 数据维度

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTypeDesc() {
        return TypeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        TypeDesc = typeDesc;
    }

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer num) {
        Num = num;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public String getDataDim() {
        return DataDim;
    }

    public void setDataDim(String dataDim) {
        DataDim = dataDim;
    }
}
