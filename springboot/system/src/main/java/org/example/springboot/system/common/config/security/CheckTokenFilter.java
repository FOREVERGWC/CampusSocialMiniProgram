package org.example.springboot.system.common.config.security;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.springboot.common.common.annotation.Anonymous;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.service.ITokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 令牌过滤器
 */
@Component
public class CheckTokenFilter extends OncePerRequestFilter {
    @Resource
    private ITokenService tokenService;
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    String[] whiteList = {
            "/captcha", "/email/**",
            "/login", "/login/wechat", "/register", "/password/reset",
            "/dict/data/list",
            "/car/page", "/car",
            "/static/**", "/file/**",
            "/doc.html", "/favicon.ico", "/webjars/**", "/swagger-resources", "/v3/api-docs/**"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        // 白名单
        String uri = request.getRequestURI();
        if (Arrays.stream(whiteList).anyMatch(url -> PATH_MATCHER.match(url, uri))) {
            filterChain.doFilter(request, response);
            return;
        }
        // 匿名访问
        HandlerMethod handler = getHandlerMethod(request);
        if (handler != null) {
            Object bean = handler.getBean();
            Method method = handler.getMethod();
            Anonymous classAnonymous = bean.getClass().getAnnotation(Anonymous.class);
            Anonymous methodAnnotation = method.getAnnotation(Anonymous.class);
            if (classAnonymous != null || methodAnnotation != null) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        // 认证
        LoginUser user = tokenService.getLoginUser(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @SneakyThrows
    private HandlerMethod getHandlerMethod(HttpServletRequest request) {
        HandlerExecutionChain handlerChain = requestMappingHandlerMapping.getHandler(request);
        if (handlerChain == null) {
            return null;
        }
        return (HandlerMethod) handlerChain.getHandler();
    }
}
