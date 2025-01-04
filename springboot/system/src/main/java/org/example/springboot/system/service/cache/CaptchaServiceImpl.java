package org.example.springboot.system.service.cache;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.example.springboot.common.common.Constants;
import org.example.springboot.common.common.enums.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements ICaptchaService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${captcha.enabled}")
    private Boolean captchaEnabled;

    @Override
    public void setEmailRegisterCode(String email, String code) {
        String key = getEmailRegisterKey(email);
        redisTemplate.opsForValue().set(key, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public String getEmailRegisterCode(String email) {
        String key = getEmailRegisterKey(email);
        return Convert.toStr(redisTemplate.opsForValue().get(key), "");
    }

    @Override
    public void validateEmailRegisterCode(String email, String code) {
        String key = getEmailRegisterKey(email);
        String captcha = Convert.toStr(redisTemplate.opsForValue().get(key), "");
        if (!StrUtil.equalsIgnoreCase(captcha, code)) {
            throw new InternalAuthenticationServiceException(ResultCode.REGISTER_CODE_ERROR.getMsg());
        }
        redisTemplate.delete(key);
    }


    @Override
    public void setUuidLoginCode(String uuid, String code) {
        String key = getUuidLoginKey(uuid);
        redisTemplate.opsForValue().set(key, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public String getUuidLoginCode(String uuid) {
        String key = getUuidLoginKey(uuid);
        return Convert.toStr(redisTemplate.opsForValue().get(key), "");
    }

    @Override
    public void validateUuidLoginCode(String uuid, String code) {
        if (!captchaEnabled) {
            return;
        }
        String key = getUuidLoginKey(uuid);
        String captcha = Convert.toStr(redisTemplate.opsForValue().get(key), "");
        if (!StrUtil.equalsIgnoreCase(captcha, code)) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_CODE_ERROR.getMsg());
        }
        redisTemplate.delete(key);
    }

    @Override
    public void setEmailLoginCode(String email, String code) {
        String key = getEmailLoginKey(email);
        redisTemplate.opsForValue().set(key, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public String getEmailLoginCode(String email) {
        String key = getEmailLoginKey(email);
        return Convert.toStr(redisTemplate.opsForValue().get(key), "");
    }

    @Override
    public void validateEmailLoginCode(String email, String code) {
        String key = getEmailLoginKey(email);
        String captcha = Convert.toStr(redisTemplate.opsForValue().get(key), "");
        if (!StrUtil.equalsIgnoreCase(captcha, code)) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_EMAIL_CODE_ERROR.getMsg());
        }
        redisTemplate.delete(key);
    }

    @Override
    public void setEmailResetCode(String email, String code) {
        String key = getEmailResetKey(email);
        redisTemplate.opsForValue().set(key, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public String getEmailResetCode(String email) {
        String key = getEmailResetKey(email);
        return Convert.toStr(redisTemplate.opsForValue().get(key), "");
    }

    @Override
    public void validateEmailResetCode(String email, String code) {
        String key = getEmailResetKey(email);
        String captcha = Convert.toStr(redisTemplate.opsForValue().get(key), "");
        if (!StrUtil.equalsIgnoreCase(captcha, code)) {
            throw new InternalAuthenticationServiceException(ResultCode.RESET_EMAIL_CODE_ERROR.getMsg());
        }
        redisTemplate.delete(key);
    }

    @Override
    public void setPhoneLoginCode(String phone, String code) {
        String key = getPhoneLogin(phone);
        redisTemplate.opsForValue().set(key, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public void validatePhoneLoginCode(String phone, String code) {
        String key = getPhoneLogin(phone);
        String captcha = Convert.toStr(redisTemplate.opsForValue().get(key), "");
        if (!StrUtil.equalsIgnoreCase(captcha, code)) {
            throw new InternalAuthenticationServiceException(ResultCode.LOGIN_PHONE_CODE_ERROR.getMsg());
        }
        redisTemplate.delete(key);
    }

    private String getEmailRegisterKey(String email) {
        return Constants.CAPTCHA_EMAIL_REGISTER_KEY + email;
    }

    private String getUuidLoginKey(String uuid) {
        return Constants.CAPTCHA_UUID_LOGIN_KEY + uuid;
    }

    private String getEmailLoginKey(String email) {
        return Constants.CAPTCHA_EMAIL_LOGIN_KEY + email;
    }

    private String getEmailResetKey(String email) {
        return Constants.CAPTCHA_EMAIL_RESET_KEY + email;
    }

    private String getPhoneLogin(String phone) {
        return Constants.CAPTCHA_PHONE_LOGIN_KEY + phone;
    }
}
