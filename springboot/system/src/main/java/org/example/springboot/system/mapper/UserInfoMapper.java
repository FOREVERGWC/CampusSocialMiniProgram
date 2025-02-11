package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.UserInfo;

/**
 * <p>
 * 用户信息Mapper接口
 * </p>
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
