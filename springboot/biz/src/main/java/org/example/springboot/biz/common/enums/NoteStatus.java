package org.example.springboot.biz.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 文章状态
 */
@Getter
@AllArgsConstructor
public enum NoteStatus {
    /**
     * 未发布
     */
    UNPUBLISHED("0", "未发布"),
    /**
     * 已发布
     */
    PUBLISHED("1", "已发布"),
    /**
     * 定时发布
     */
    SCHEDULED("2", "定时发布");

    private static final Map<String, NoteStatus> map = new HashMap<>();

    static {
        for (NoteStatus item : NoteStatus.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static NoteStatus jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static NoteStatus getByCode(String code) {
        return map.get(code);
    }
}
