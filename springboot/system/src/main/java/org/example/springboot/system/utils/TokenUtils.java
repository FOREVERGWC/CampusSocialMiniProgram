package org.example.springboot.system.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;

import java.util.Date;

/**
 * 令牌工具类
 */
public class TokenUtils {
    private static final String secretKey = "aasfasfasfasfasgmkgiosufaokm,yrdfl;.h";
    private static final String subject = "博客管理系统";

    /**
     * 生成令牌
     *
     * @param uuid 唯一标识符
     * @return 令牌
     */
    public static String createToken(String uuid) {
        return JWT.create()
                .withAudience(uuid)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .withSubject(subject)
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 校验令牌
     *
     * @param token 令牌
     */
    public static void verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(ResultCode.TOKEN_VERIFY_ERROR);
        }
    }
}

