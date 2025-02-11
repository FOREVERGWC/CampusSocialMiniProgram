package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.UserSchool;

/**
 * <p>
 * 用户学校Mapper接口
 * </p>
 */
@Mapper
public interface UserSchoolMapper extends BaseMapper<UserSchool> {

}
