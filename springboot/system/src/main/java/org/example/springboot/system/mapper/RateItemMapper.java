package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.RateItem;

/**
 * <p>
 * 评分项Mapper接口
 * </p>
 */
@Mapper
public interface RateItemMapper extends BaseMapper<RateItem> {

}
