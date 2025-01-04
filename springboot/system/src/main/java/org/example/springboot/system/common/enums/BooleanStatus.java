package org.example.springboot.system.common.enums;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态
 */
@Getter
@AllArgsConstructor
public enum BooleanStatus {
    /**
     * 失败
     */
    FAILURE(false, "失败"),
    /**
     * 未知
     */
    UNKNOWN(null, "未知"),
    /**
     * 成功
     */
    SUCCESS(true, "成功");

    private static final Map<Boolean, BooleanStatus> map = new HashMap<>();

    static {
        for (BooleanStatus item : BooleanStatus.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final Boolean code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static BooleanStatus jacksonInstance(final JsonNode jsonNode) {
        Boolean code = Convert.convert(Boolean.class, jsonNode.asText());
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static BooleanStatus getByCode(Boolean code) {
        return map.get(code);
    }
}
