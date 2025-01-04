package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountRate;

/**
 * <p>
 * 评分量Mapper接口
 * </p>
 */
@Mapper
public interface CountRateMapper extends BaseMapper<CountRate> {

}
