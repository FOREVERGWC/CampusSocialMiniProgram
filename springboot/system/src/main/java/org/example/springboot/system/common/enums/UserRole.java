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
 * 账户角色
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    /**
     * 管理员
     */
    ADMIN("0", "管理员"),
    /**
     * 用户
     */
    USER("1", "用户");

    private static final Map<String, UserRole> map = new HashMap<>();

    static {
        for (UserRole item : UserRole.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static UserRole jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static UserRole getByCode(String code) {
        return map.get(code);
    }
}
