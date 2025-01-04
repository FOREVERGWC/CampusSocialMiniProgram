package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.Role;

/**
 * <p>
 * 角色Mapper接口
 * </p>
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
