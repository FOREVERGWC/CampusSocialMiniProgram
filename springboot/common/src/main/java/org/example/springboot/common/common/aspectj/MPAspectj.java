package org.example.springboot.common.common.aspectj;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Mybatis Plus单条查询切面
 */
@Aspect
@Component
public class MPAspectj {
    @Pointcut("execution(public * com.baomidou.mybatisplus.core.mapper.BaseMapper.selectOne(..))")
    public void selectOneAspect() {
    }

    @Before("selectOneAspect()")
    public void beforeSelect(JoinPoint point) {
        Object arg = point.getArgs()[0];
        if (arg instanceof AbstractWrapper<?, ?, ?> wrapper) {
            wrapper.last("LIMIT 1");
        }
    }
}
