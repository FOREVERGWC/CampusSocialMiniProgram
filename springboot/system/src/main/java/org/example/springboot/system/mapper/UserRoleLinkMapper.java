package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.UserRoleLink;

/**
 * <p>
 * 用户、角色关系Mapper接口
 * </p>
 */
@Mapper
public interface UserRoleLinkMapper extends BaseMapper<UserRoleLink> {

}
