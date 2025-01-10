package org.example.springboot.common.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息响应码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功！"),
    /**
     * 参数异常
     */
    PARAM_ERROR(400, "参数异常"),
    /**
     * 校验失败！令牌不存在
     */
    TOKEN_NOT_FOUND_ERROR(401, "校验失败！令牌不存在"),
    /**
     * 校验失败！令牌无效
     */
    TOKEN_VERIFY_ERROR(401, "校验失败！令牌无效"),
    /**
     * 无效的token
     */
    TOKEN_INVALID_ERROR(401, "无效的token"),
    /**
     * token验证失败，请重新登录
     */
    TOKEN_CHECK_ERROR(401, "token验证失败，请重新登录"),
    /**
     * 登录失败！用户名或密码错误
     */
    LOGIN_USERNAME_OR_PASSWORD_ERROR(500, "登录失败！用户名或密码错误"),
    /**
     * 登录失败！验证码错误
     */
    LOGIN_CODE_ERROR(500, "登录失败！验证码错误"),
    /**
     * 登录失败！邮箱或验证码错误
     */
    LOGIN_EMAIL_CODE_ERROR(500, "登录失败！邮箱或验证码错误"),
    /**
     * 登录失败！手机或验证码错误
     */
    LOGIN_PHONE_CODE_ERROR(500, "登录失败！手机或验证码错误"),
    /**
     * 登录失败！账户过期
     */
    LOGIN_ACCOUNT_EXPIRED_ERROR(702, "登录失败！账户过期"),
    /**
     * 登录失败！证书过期
     */
    LOGIN_CREDENTIALS_EXPIRED_ERROR(703, "登录失败！证书过期"),
    /**
     * 登录失败！该账户已被禁用
     */
    LOGIN_DISABLED_ERROR(704, "登录失败！该账户已被禁用"),
    /**
     * 登录失败！该账户已被锁定
     */
    LOGIN_LOCKED_ERROR(705, "登录失败！该账户已被锁定"),
    /**
     * 发送失败！该邮箱已注册
     */
    EMAIL_HAS_USED_ERROR(500, "发送失败！该邮箱已注册"),
    /**
     * 发送失败！该邮箱已发送，请注意查收
     */
    EMAIL_HAS_SEND_ERROR(500, "发送失败！该邮箱已发送，请注意查收"),
    /**
     * 注册失败！验证码错误
     */
    REGISTER_CODE_ERROR(800, "注册失败！验证码错误"),
    /**
     * 注册失败！确认密码不一致
     */
    REGISTER_CONFIRM_ERROR(801, "注册失败！确认密码不一致"),
    /**
     * 修改失败！手机或验证码错误
     */
    RESET_EMAIL_CODE_ERROR(900, "修改失败！手机或验证码错误"),
    /**
     * 修改失败！确认密码不一致
     */
    RESET_CONFIRM_ERROR(901, "修改失败！确认密码不一致"),
    /**
     * 参数缺失
     */
    PARAM_LOST_ERROR(4001, "参数缺失"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),
    /**
     * 操作失败！用户不存在
     */
    USER_NOT_FOUND_ERROR(1000, "操作失败！用户不存在"),
    /**
     * 用户名已存在
     */
    USER_EXIST_ERROR(1001, "用户名已存在"),
    /**
     * 操作失败！用户未登录
     */
    USER_NOT_LOGIN(1002, "操作失败！用户未登录"),
    /**
     * 账号或密码错误
     */
    USER_ACCOUNT_ERROR(1003, "账号或密码错误"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST_ERROR(1004, "用户不存在"),
    /**
     * 操作失败！用户无权限
     */
    USER_NOT_HAS_PERMISSION(1005, "操作失败！用户无权限"),
    /**
     * 原密码输入错误
     */
    PARAM_PASSWORD_ERROR(1006, "原密码输入错误"),
    /**
     * 账户余额不足，请到个人中心充值
     */
    ACCOUNT_LOW_ERROR(1007, "账户余额不足，请到个人中心充值"),
    /**
     * 操作失败！角色不存在
     */
    ROLE_NOT_FOUND_ERROR(500, "操作失败！角色不存在"),
    /**
     * 删除失败！该角色已分配用户
     */
    ROLE_DELETE_ERROR(500, "删除失败！该角色已分配用户"),
    /**
     * 操作失败！菜单不存在
     */
    MENU_NOT_FOUND_ERROR(500, "操作失败！菜单不存在"),
    /**
     * 操作失败！权限不存在
     */
    PERMISSION_NOT_FOUND_ERROR(500, "操作失败！权限不存在"),
    /**
     * 操作失败！字典类型不存在
     */
    DICT_TYPE_NOT_FOUND_ERROR(500, "操作失败！字典类型不存在"),
    /**
     * 操作失败！字典数据不存在
     */
    DICT_DATA_NOT_FOUND_ERROR(500, "操作失败！字典数据不存在"),
    /**
     * 操作失败！文章不存在
     */
    ARTICLE_NOT_FOUND_ERROR(500, "操作失败！文章不存在"),
    /**
     * 操作失败！笔记不存在
     */
    NOTE_NOT_FOUND_ERROR(500, "操作失败！笔记不存在"),
    /**
     * 操作失败！订单不存在
     */
    ORDER_NOT_FOUND_ERROR(500, "操作失败！订单不存在"),
    /**
     * 操作失败！订单付款状态异常
     */
    ORDER_PAY_STATUS_ERROR(500, "操作失败！订单付款状态异常"),
    /**
     * 操作失败！订单退款状态异常
     */
    ORDER_REFUND_STATUS_ERROR(500, "操作失败！订单退款状态异常"),
    /**
     * 操作失败！验车状态异常
     */
    CAR_AUDITE_STATUS_ERROR(500, "操作失败！验车状态异常"),
    /**
     * 下单失败！当前车辆未经审查
     */
    ORDER_ADD_ERROR(500, "下单失败！当前车辆未经审查");

    private final Integer code;
    private final String msg;
}
