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
 * 业务类型
 */
@Getter
@AllArgsConstructor
public enum BizType {
    /**
     * 其他
     */
    OTHER(0, "其他"),
    /**
     * 头像
     */
    AVATAR(1, "头像"),
    /**
     * 评论
     */
    SYS_COMMENT(5, "评论"),
    /**
     * 笔记
     */
    BIZ_NOTE(8, "笔记"),
    /**
     * 评分
     */
    BIZ_RATE(9, "评分"),
    /**
     * 评分项
     */
    BIZ_RATE_ITEM(10, "评分项"),
    /**
     * 组局
     */
    BIZ_PARTNER(11, "组局");


    private static final Map<Integer, BizType> map = new HashMap<>();

    static {
        for (BizType item : BizType.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static BizType jacksonInstance(final JsonNode jsonNode) {
        Integer code = Convert.toInt(jsonNode.asText());
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static BizType getByCode(Integer code) {
        return map.get(code);
    }
}
