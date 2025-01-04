package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.CountFavorite;

/**
 * <p>
 * 收藏量Mapper接口
 * </p>
 */
@Mapper
public interface CountFavoriteMapper extends BaseMapper<CountFavorite> {

}
