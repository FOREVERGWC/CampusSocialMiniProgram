package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.system.domain.entity.Favorite;

/**
 * <p>
 * 收藏Mapper接口
 * </p>
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}
