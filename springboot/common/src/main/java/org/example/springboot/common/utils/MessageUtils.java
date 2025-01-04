package org.example.springboot.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {
    public static String getMsg(String code, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
