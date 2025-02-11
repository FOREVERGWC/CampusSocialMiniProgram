package org.example.springboot.system.service;

/**
 * <p>
 * 支付宝服务类
 * </p>
 */
public interface IAlipayService {
    /**
     * 电脑网站支付订单
     *
     * @param outTradeNo  商户订单号
     * @param totalAmount 总金额
     * @param subject     主题
     * @param body        内容
     * @return 结果
     */
    String orderPayPc(String outTradeNo, String totalAmount, String subject, String body);

    /**
     * APP支付订单
     *
     * @param outTradeNo  商户订单号
     * @param totalAmount 总金额
     * @param subject     主题
     * @param body        内容
     * @return 结果
     */
    String orderPayApp(String outTradeNo, String totalAmount, String subject, String body);

    /**
     * 手机网站支付订单
     *
     * @param outTradeNo  商户订单号
     * @param totalAmount 总金额
     * @param subject     主题
     * @param body        内容
     * @return 结果
     */
    String orderPayMobile(String outTradeNo, String totalAmount, String subject, String body);

    /**
     * 查询订单支付状态
     *
     * @param outTradeNo 商户订单号
     * @return 结果
     */
    String getOrderStatus(String outTradeNo);
}
