package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.ActivityCategory;

/**
 * <p>
 * 活动类别Mapper接口
 * </p>
 */
@Mapper
public interface ActivityCategoryMapper extends BaseMapper<ActivityCategory> {

}
