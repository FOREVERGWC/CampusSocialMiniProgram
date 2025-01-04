package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.Follow;

/**
 * <p>
 * 关注Mapper接口
 * </p>
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

}
