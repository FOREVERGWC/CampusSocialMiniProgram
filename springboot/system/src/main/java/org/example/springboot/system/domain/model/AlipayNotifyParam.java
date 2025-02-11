package org.example.springboot.system.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付宝通知参数
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(name = "支付宝通知参数实体", description = "支付宝通知参数")
public class AlipayNotifyParam implements Serializable {
    /**
     * APPID
     */
    @Schema(description = "APPID")
    private String appId;
    /**
     * 支付宝交易凭证号
     */
    @Schema(description = "支付宝交易凭证号")
    private String tradeNo;
    /**
     * 商户订单号
     */
    @Schema(description = "商户订单号")
    private String outTradeNo;
    /**
     * 商户业务ID
     */
    @Schema(description = "商户业务ID")
    private String outBizNo;
    /**
     * 买家支付宝用户号
     */
    @Schema(description = "买家支付宝用户号")
    private String buyerId;
    /**
     * 买家支付宝账号
     */
    @Schema(description = "买家支付宝账号")
    private String buyerLogonId;
    /**
     * 卖家支付宝用户号
     */
    @Schema(description = "卖家支付宝用户号")
    private String sellerId;
    /**
     * 卖家支付宝账号
     */
    @Schema(description = "卖家支付宝账号")
    private String sellerEmail;
    /**
     * 当前交易状态
     */
    @Schema(description = "当前交易状态")
    private String tradeStatus;
    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal totalAmount;
    /**
     * 商家收款
     */
    @Schema(description = "商家收款")
    private BigDecimal receiptAmount;
    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal buyerPayAmount;
    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal refundFee;
    /**
     * 商品标题、交易标题、订单标题、订单关键字
     */
    @Schema(description = "商品标题、交易标题、订单标题、订单关键字")
    private String subject;
    /**
     * 订单的备注、描述、明细
     */
    @Schema(description = "订单的备注、描述、明细")
    private String body;
    /**
     * 交易创建时间
     */
    @Schema(description = "交易创建时间")
    private LocalDateTime gmtCreate;
    /**
     * 买家付款时间
     */
    @Schema(description = "买家付款时间")
    private LocalDateTime gmtPayment;
    /**
     * 交易退款时间
     */
    @Schema(description = "交易退款时间")
    private LocalDateTime gmtRefund;
    /**
     * 交易结束时间
     */
    @Schema(description = "交易结束时间")
    private LocalDateTime gmtClose;
    /**
     * 支付渠道金额信息
     */
    @Schema(description = "支付渠道金额信息")
    private String fundBillList;
    /**
     * 公共回传参数
     */
    @Schema(description = "公共回传参数")
    private String passbackParams;
}
