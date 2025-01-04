package org.example.springboot.common.common.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;

/**
 * Swagger文档配置类
 */
@Slf4j
@Configuration
public class SwaggerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        String hostAddress = Inet4Address.getLocalHost().getHostAddress(); // 获取端口号
        int port = event.getWebServer().getPort();
        String applicationName = event.getApplicationContext().getApplicationName(); // 获取应用名
        log.info("-----------------------------------------------------");
        log.info("-\t本地地址：http://{}:{}{}", hostAddress, port, applicationName);
        log.info("-\tSwagger文档：http://{}:{}{}/doc.html", hostAddress, port, applicationName);
        log.info("-----------------------------------------------------");
    }
}
