package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.Note;

/**
 * <p>
 * 笔记Mapper接口
 * </p>
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

}
