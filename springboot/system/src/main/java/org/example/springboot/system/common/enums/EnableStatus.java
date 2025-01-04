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
 * 启用状态
 */
@Getter
@AllArgsConstructor
public enum EnableStatus {
    /**
     * 禁用
     */
    DISABLE("0", "禁用"),
    /**
     * 正常
     */
    NORMAL("1", "正常");

    private static final Map<String, EnableStatus> map = new HashMap<>();

    static {
        for (EnableStatus item : EnableStatus.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static EnableStatus jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static EnableStatus getByCode(String code) {
        return map.get(code);
    }
}
