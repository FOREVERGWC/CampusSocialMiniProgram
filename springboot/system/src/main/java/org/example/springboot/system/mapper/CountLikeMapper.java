package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountLike;

/**
 * <p>
 * 点赞量Mapper接口
 * </p>
 */
@Mapper
public interface CountLikeMapper extends BaseMapper<CountLike> {

}
