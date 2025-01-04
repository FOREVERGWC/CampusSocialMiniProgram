package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountDislike;

/**
 * <p>
 * 点踩量Mapper接口
 * </p>
 */
@Mapper
public interface CountDislikeMapper extends BaseMapper<CountDislike> {

}
