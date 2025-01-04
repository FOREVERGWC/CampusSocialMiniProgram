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
 * 菜单类型
 */
@Getter
@AllArgsConstructor
public enum MenuType {
    /**
     * 目录
     */
    DIR("1", "目录"),
    /**
     * 菜单
     */
    MENU("2", "菜单"),
    /**
     * 按钮
     */
    BUTTON("3", "按钮");

    private static final Map<String, MenuType> map = new HashMap<>();

    static {
        for (MenuType item : MenuType.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static MenuType jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static MenuType getByCode(String code) {
        return map.get(code);
    }
}
