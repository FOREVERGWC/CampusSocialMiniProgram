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
 * 登录类型
 */
@Getter
@AllArgsConstructor
public enum LoginType {
    /**
     * 账密
     */
    USERNAME_PASSWORD("1", "账密"),
    /**
     * 邮箱验证码
     */
    EMAIL_CODE("2", "邮箱验证码"),
    /**
     * 手机验证码
     */
    PHONE_CODE("3", "手机验证码");

    private static final Map<String, LoginType> map = new HashMap<>();

    static {
        for (LoginType item : LoginType.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static LoginType jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static LoginType getByCode(String code) {
        return map.get(code);
    }
}
