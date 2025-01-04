package org.example.springboot.system.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum Gender {
    /**
     * 女
     */
    FEMALE("0", "女"),
    /**
     * 男
     */
    MALE("1", "男"),
    /**
     * 未知
     */
    UNKNOWN("2", "未知");

    private static final Map<String, Gender> map = new HashMap<>();

    static {
        for (Gender item : Gender.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static Gender jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static Gender getByCode(String code) {
        return map.get(code);
    }
}
