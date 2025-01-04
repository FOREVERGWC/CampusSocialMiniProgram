package org.example.springboot.system.service.cache;

public interface ICaptchaService {
    /**
     * 设置邮箱注册验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void setEmailRegisterCode(String email, String code);

    /**
     * 获取邮箱注册验证码
     *
     * @param email 邮箱
     * @return 验证码
     */
    String getEmailRegisterCode(String email);

    /**
     * 校验邮箱注册验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void validateEmailRegisterCode(String email, String code);

    /**
     * 设置UUID验证码
     *
     * @param uuid 唯一标识
     * @param code 验证码
     */
    void setUuidLoginCode(String uuid, String code);

    /**
     * 获取UUID验证码
     *
     * @param uuid 唯一标识
     * @return 验证码
     */
    String getUuidLoginCode(String uuid);

    /**
     * 校验UUID验证码
     *
     * @param uuid 唯一标识
     * @param code 验证码
     */
    void validateUuidLoginCode(String uuid, String code);

    /**
     * 设置邮箱登录验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void setEmailLoginCode(String email, String code);

    /**
     * 获取邮箱登录验证码
     *
     * @param email 邮箱
     * @return 验证码
     */
    String getEmailLoginCode(String email);

    /**
     * 校验邮箱登录验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void validateEmailLoginCode(String email, String code);

    /**
     * 设置邮箱改密验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void setEmailResetCode(String email, String code);

    /**
     * 获取邮箱改密验证码
     *
     * @param email 邮箱
     * @return 验证码
     */
    String getEmailResetCode(String email);

    /**
     * 校验邮箱改密验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void validateEmailResetCode(String email, String code);

    /**
     * 设置手机登录验证码
     *
     * @param phone 手机
     * @param code  验证码
     */
    void setPhoneLoginCode(String phone, String code);

    /**
     * 校验手机登录验证码
     *
     * @param phone 手机
     * @param code  验证码
     */
    void validatePhoneLoginCode(String phone, String code);
}
