package com.cbrc.dashboard.enums;

public enum ItemStatusEnum {
    VALID("有效", 1),
    INVALID("无效", 2);

    private Integer code;
    private String name;

    ItemStatusEnum(String name, Integer code) {
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
}
