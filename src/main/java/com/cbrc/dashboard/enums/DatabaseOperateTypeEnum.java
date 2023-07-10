package com.cbrc.dashboard.enums;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.enums
 * @author: Herry
 * @Date: 2019/3/7 13:00
 * @Description: TODO
 */
public enum DatabaseOperateTypeEnum {

    // 数据库更新类型
    ADD("新增", 1),
    UPDATE("更新", 2),
    DEL("删除", 3);

    private Integer code;
    private String name;

    DatabaseOperateTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ItemStatusEnum getByCode(Integer code) {
        for (ItemStatusEnum item : ItemStatusEnum.values()) {
            if (code == item.getCode()) {
                return item;
            }
        }
        return null;
    }

    public static ItemStatusEnum getByName(String name) {
        for (ItemStatusEnum item : ItemStatusEnum.values()) {
            if (name.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }
}
