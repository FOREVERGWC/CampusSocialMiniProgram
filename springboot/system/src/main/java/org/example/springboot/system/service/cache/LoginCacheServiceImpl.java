package org.example.springboot.system.service.cache;

import cn.hutool.core.convert.Convert;
import com.auth0.jwt.JWT;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.model.LoginUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginCacheServiceImpl implements ILoginCacheService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addFailureCount(String principal) {
        String key = "login:count:" + principal;
        Long count = redisTemplate.opsForValue().increment(key);
        if (Objects.equals(1L, count)) {
            redisTemplate.expire(key, 24, TimeUnit.HOURS);
        }
    }

    @Override
    public boolean getAccountNonLocked(String principal) {
        String key = "login:count:" + principal;
        Object value = redisTemplate.opsForValue().get(key);
        Integer count = Convert.toInt(value, 0);
        return count < 5;
    }

    @Override
    public void setLoginUser(LoginUser user) {
        String key = "login:user:" + user.getUuid();
        redisTemplate.opsForValue().set(key, user, 2, TimeUnit.HOURS);
    }

    @Override
    public void removeLoginUser(String uuid) {
        String key = "login:user:" + uuid;
        redisTemplate.delete(key);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        String uuid = JWT.decode(token).getAudience().getFirst();
        String key = "login:user:" + uuid;
        return (LoginUser) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long getUserIdByToken(String token) {
        LoginUser user = getLoginUser(token);
        return user == null ? null : user.getId();
    }

    @Override
    public String getUsernameByToken(String token) {
        LoginUser user = getLoginUser(token);
        return user == null ? null : user.getUsername();
    }
}
