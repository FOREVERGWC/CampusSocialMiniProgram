package org.example.springboot.system.common.config;

import jakarta.annotation.Resource;
import org.example.springboot.system.common.config.security.*;
import org.example.springboot.system.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    //    @Resource
//    private CorsFilter corsFilter;
    @Resource
    private CheckTokenFilter checkTokenFilter;
    @Resource
    private LoginAuthenticationHandler loginAuthenticationHandler;
    @Resource
    private LoginAccessDefineHandler loginAccessDefineHandler;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandlerImpl;
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public EmailCodeAuthenticationProvider emailCodeAuthenticationProvider() {
        return new EmailCodeAuthenticationProvider(userDetailsService);
    }

    @Bean
    public PhoneCodeAuthenticationProvider phoneCodeAuthenticationProvider() {
        return new PhoneCodeAuthenticationProvider(userDetailsService);
    }

    @Bean
    public WechatOpenIdAuthenticationProvider wechatOpenIdAuthenticationProvider() {
        return new WechatOpenIdAuthenticationProvider(userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.authenticationProvider(daoAuthenticationProvider());
        authBuilder.authenticationProvider(emailCodeAuthenticationProvider());
        authBuilder.authenticationProvider(phoneCodeAuthenticationProvider());
        authBuilder.authenticationProvider(wechatOpenIdAuthenticationProvider());
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandlerImpl))
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(corsFilter, CheckTokenFilter.class)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/captcha", "/email/**").permitAll()
                                .requestMatchers("/login", "/login/wechat", "/register", "/password/reset").permitAll()
                                .requestMatchers("/dict/data/list").permitAll()
                                .requestMatchers("/static/**", "/file/**").permitAll()
                                .requestMatchers("/doc.html", "/favicon.ico", "/webjars/**", "/swagger-resources", "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // 匿名用户访问处理
                        .authenticationEntryPoint(loginAuthenticationHandler)
                        // 认证用户无权访问处理
                        .accessDeniedHandler(loginAccessDefineHandler)
                )
//                .cors(item -> item.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::disable)))
                .headers(headers -> headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::sameOrigin)));
        return http.build();
    }
}
