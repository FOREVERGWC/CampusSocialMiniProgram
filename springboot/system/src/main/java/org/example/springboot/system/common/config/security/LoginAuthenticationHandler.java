package org.example.springboot.system.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.instance.ObjectMapperInstance;
import org.example.springboot.common.domain.Result;
import org.example.springboot.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 匿名用户访问处理器
 */
@Component
public class LoginAuthenticationHandler implements AuthenticationEntryPoint {
    ObjectMapper objectMapper = ObjectMapperInstance.INSTANCE.getObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        ServletUtils.write(response, objectMapper.writeValueAsString(Result.error(ResultCode.TOKEN_VERIFY_ERROR)));
    }
}
