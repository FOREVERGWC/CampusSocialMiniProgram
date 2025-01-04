package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.RoleMenuLink;

/**
 * <p>
 * 角色、菜单关系Mapper接口
 * </p>
 */
@Mapper
public interface RoleMenuLinkMapper extends BaseMapper<RoleMenuLink> {

}
