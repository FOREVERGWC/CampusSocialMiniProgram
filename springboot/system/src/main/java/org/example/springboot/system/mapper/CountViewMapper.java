package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountView;

/**
 * <p>
 * 浏览量Mapper接口
 * </p>
 */
@Mapper
public interface CountViewMapper extends BaseMapper<CountView> {

}
