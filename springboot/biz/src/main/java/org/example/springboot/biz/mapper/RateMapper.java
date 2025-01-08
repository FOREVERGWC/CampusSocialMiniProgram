package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.Rate;

/**
 * <p>
 * 评分Mapper接口
 * </p>
 */
@Mapper
public interface RateMapper extends BaseMapper<Rate> {

}
