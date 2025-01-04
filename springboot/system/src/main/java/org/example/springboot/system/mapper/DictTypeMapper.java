package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.DictType;

/**
 * <p>
 * 字典类型Mapper接口
 * </p>
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

}
