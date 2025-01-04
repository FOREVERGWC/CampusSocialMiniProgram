package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.Comment;

/**
 * <p>
 * 评论Mapper接口
 * </p>
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
