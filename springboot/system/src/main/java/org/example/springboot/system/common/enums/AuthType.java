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
 * 认证方式
 */
@Getter
@AllArgsConstructor
public enum AuthType {
    /**
     * 微信
     */
    WECHAT(0, "微信");

    private static final Map<Integer, AuthType> map = new HashMap<>();

    static {
        for (AuthType item : AuthType.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static AuthType jacksonInstance(final JsonNode jsonNode) {
        Integer code = Convert.toInt(jsonNode.asText());
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static AuthType getByCode(Integer code) {
        return map.get(code);
    }
}
