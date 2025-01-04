package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.system.domain.entity.LogLogin;

import java.util.List;

/**
 * <p>
 * 登录日志Mapper接口
 * </p>
 */
@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {
    /**
     * 深度分页
     *
     * @param offset  偏移量
     * @param rows    行数
     * @param wrapper 包装器
     * @return 结果
     */
    @Select("SELECT t1.id, t1.login_type, t1.os, t1.browser, t1.ip, t1.location, t1.status, t1.msg, t1.create_by,t1.create_time, t1.update_by, t1.update_time, t1.remark FROM sys_log_login AS t1 JOIN " +
            "(SELECT id FROM sys_log_login ORDER BY create_time DESC LIMIT #{offset}, #{rows}) t2 ON t1.id = t2.id;")
    List<LogLogin> getPageList(Long offset, Long rows, @Param(Constants.WRAPPER) Wrapper<LogLogin> wrapper);
    // TODO 包装器未生效，传入IPage<LogLogin>无法分页，${ew.customSqlSegment}无效
}
