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
 * 逻辑删除
 */
@Getter
@AllArgsConstructor
public enum DeleteEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DELETED(1, "删除");

    private static final Map<Integer, DeleteEnum> map = new HashMap<>();

    static {
        for (DeleteEnum item : DeleteEnum.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static DeleteEnum jacksonInstance(final JsonNode jsonNode) {
        Integer code = Convert.convert(Integer.class, jsonNode.asText());
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static DeleteEnum getByCode(Integer code) {
        return map.get(code);
    }
}
