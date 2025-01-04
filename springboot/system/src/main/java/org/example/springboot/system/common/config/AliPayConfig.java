package org.example.springboot.system.common.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AliPayConfig {
    /**
     * APPID
     */
    @Value("${alipay.appId}")
    private String appId;
    /**
     * 商家账号
     */
    @Value("${alipay.pid}")
    private String pid;
    /**
     * 商户私钥
     */
    @Value("${alipay.privateKey}")
    private String privateKey;
    /**
     * 支付宝公钥
     */
    @Value("${alipay.publicKey}")
    private String publicKey;
    /**
     * 服务器异步通知页面路径
     */
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    /**
     * 页面跳转同步通知页面路径
     */
    @Value("${alipay.returnUrl}")
    private String returnUrl;
    /**
     * 签名方式
     */
    @Value("${alipay.signType}")
    private String signType;
    /**
     * 字符编码格式
     */
    @Value("${alipay.charset}")
    private String charset;
    /**
     * 请求格式
     */
    @Value("${alipay.format}")
    private String format;
    /**
     * 支付宝网关
     */
    @Value("${alipay.gateway}")
    private String gateway;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(gateway, appId, privateKey, format, charset, publicKey, signType);
    }
}
