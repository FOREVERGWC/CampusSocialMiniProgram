package org.example.springboot.common.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.example.springboot.common.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP配置类
 */
@Configuration
public class MPConfig {
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.setMaxLimit(Constants.PER_WRITE_ROW_COUNT.longValue());
        return interceptor;
    }

    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 乐观锁拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页拦截器
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
