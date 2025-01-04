package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountComment;

/**
 * <p>
 * 评论量Mapper接口
 * </p>
 */
@Mapper
public interface CountCommentMapper extends BaseMapper<CountComment> {

}
