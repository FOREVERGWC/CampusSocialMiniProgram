package org.example.springboot.common.common;

public interface Constants {
    /**
     * 令牌
     */
    String TOKEN = "token";
    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";
    /**
     * 重置后密码
     */
    String RESET_PASSWORD = "123456";
    /**
     * Redis邮箱注册验证码键值前缀
     */
    String CAPTCHA_EMAIL_REGISTER_KEY = "captcha:email:register:";
    /**
     * Redis唯一标识符登录验证码键值前缀
     */
    String CAPTCHA_UUID_LOGIN_KEY = "captcha:uuid:login:";
    /**
     * Redis邮箱登录验证码键值前缀
     */
    String CAPTCHA_EMAIL_LOGIN_KEY = "captcha:email:login:";
    /**
     * Redis邮箱改密验证码键值前缀
     */
    String CAPTCHA_EMAIL_RESET_KEY = "captcha:email:reset:";
    /**
     * Redis手机登录验证码键值前缀
     */
    String CAPTCHA_PHONE_LOGIN_KEY = "captcha:phone:login:";
    /**
     * Redis验证码有效时长
     */
    Long CAPTCHA_EXPIRATION = 2L;
    /**
     * UTF-8编码JSON请求体
     */
    String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    /**
     * 每个文件工作簿数
     */
    Integer PER_FILE_SHEET_COUNT = 5;
    /**
     * 每次写入最大行数
     */
    Integer PER_WRITE_ROW_COUNT = 200000;
    /**
     * 每张工作簿最大行数
     */
    Integer PER_SHEET_ROW_COUNT = 200000;
    /**
     * 每个文件最大数据量
     */
    Integer PER_FILE_ROW_COUNT = PER_FILE_SHEET_COUNT * PER_SHEET_ROW_COUNT;
}
