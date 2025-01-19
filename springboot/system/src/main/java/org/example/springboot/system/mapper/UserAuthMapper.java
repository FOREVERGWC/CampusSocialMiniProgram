package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.UserAuth;

/**
 * <p>
 * 用户三方授权Mapper接口
 * </p>
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {

}
