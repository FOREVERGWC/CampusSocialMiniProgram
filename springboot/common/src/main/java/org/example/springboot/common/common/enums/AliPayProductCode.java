package org.example.springboot.common.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝产品码
 */
@Getter
@AllArgsConstructor
public enum AliPayProductCode {
    /**
     * 电脑网站支付
     */
    FAST_INSTANT_TRADE_PAY("FAST_INSTANT_TRADE_PAY", "电脑网站支付"),
    /**
     * APP支付
     */
    QUICK_MSECURITY_PAY("QUICK_MSECURITY_PAY", "APP支付"),
    /**
     * 手机网站支付
     */
    QUICK_WAP_WAY("QUICK_WAP_WAY", "手机网站支付"),
    /**
     * 当面付
     */
    FACE_TO_FACE_PAYMENT("FACE_TO_FACE_PAYMENT", "当面付");

    private static final Map<String, AliPayProductCode> map = new HashMap<>();

    static {
        for (AliPayProductCode item : AliPayProductCode.values()) {
            map.put(item.getCode(), item);
        }
    }

    @EnumValue
    private final String code;
    @JsonValue
    private final String msg;

    @JsonCreator
    private static AliPayProductCode jacksonInstance(final JsonNode jsonNode) {
        String code = jsonNode.asText();
        return map.get(code);
    }

    /**
     * 根据键获取枚举
     *
     * @param code 键
     * @return 结果
     */
    public static AliPayProductCode getByCode(String code) {
        return map.get(code);
    }
}
