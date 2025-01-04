package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.DictData;

/**
 * <p>
 * 字典数据Mapper接口
 * </p>
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

}
