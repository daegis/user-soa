package com.hnair.consumer.user.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by simple on 2016/11/29 10:57
 * 用户类型
 * @mail moneyhacker@163.com
 * @since 1.0
 */
public enum UserTypeEnum {
    COMMON_USER(0, "普通用户"),
    KU_PGC(1,"KU_PGC用户");

    @Getter
    private final  Integer type;
    @Getter
    private final  String title;
    private final static Map<Integer, UserTypeEnum> TYPE_MAP = new LinkedHashMap<>();

    private UserTypeEnum(Integer type, String title) {
        this.type =type;
        this.title =title;
    }
    static {
        for (UserTypeEnum type : UserTypeEnum.values()) {
            TYPE_MAP.put(type.getType(), type);
        }
    }

    



    public static Map<Integer, UserTypeEnum> getAllType() {
        return TYPE_MAP;
    }

    public static UserTypeEnum of(Short type) {
        if (type == null) {
            return null;
        }
        return TYPE_MAP.get(String.valueOf(type));
    }
}
