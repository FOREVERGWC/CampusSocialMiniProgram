package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.RolePermissionLink;

/**
 * <p>
 * 角色、权限关系Mapper接口
 * </p>
 */
@Mapper
public interface RolePermissionLinkMapper extends BaseMapper<RolePermissionLink> {

}
