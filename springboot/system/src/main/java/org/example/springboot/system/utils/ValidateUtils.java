package org.example.springboot.system.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.validation.*;
import org.example.springboot.common.utils.MessageUtils;
import org.example.springboot.system.domain.model.LoginBody;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.List;
import java.util.Set;

public class ValidateUtils {
    /**
     * 校验器
     *
     * @param body PC端登录请求体
     */
    public static void valid(LoginBody body) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validatorFactory = factory.getValidator();
        Set<ConstraintViolation<LoginBody>> violationSet = validatorFactory.validate(body);
        if (CollectionUtil.isEmpty(violationSet)) {
            return;
        }
        List<ConstraintViolation<LoginBody>> violationList = List.copyOf(violationSet);
        ConstraintViolation<LoginBody> violation = violationList.getFirst();
        String code = violation.getMessage();
        code = StrUtil.removePrefix(code, "{");
        code = StrUtil.removeSuffix(code, "}");
        String message = MessageUtils.getMsg(code);
        throw new InternalAuthenticationServiceException(message);
    }
}
