package org.example.springboot.system.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.instance.ObjectMapperInstance;
import org.example.springboot.common.domain.Result;
import org.example.springboot.common.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证用户无权访问处理器
 */
@Component
public class LoginAccessDefineHandler implements AccessDeniedHandler {
    ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        ServletUtils.write(response, objectMapper.writeValueAsString(Result.error(ResultCode.USER_NOT_HAS_PERMISSION)));
    }
}
