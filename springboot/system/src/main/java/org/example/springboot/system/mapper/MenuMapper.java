package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.Menu;

/**
 * <p>
 * 菜单Mapper接口
 * </p>
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
