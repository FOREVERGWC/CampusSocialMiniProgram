package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.NoteCategory;

/**
 * <p>
 * 笔记类别Mapper接口
 * </p>
 */
@Mapper
public interface NoteCategoryMapper extends BaseMapper<NoteCategory> {

}
