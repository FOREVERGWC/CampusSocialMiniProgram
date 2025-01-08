package org.example.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.biz.domain.entity.RateRecord;

/**
 * <p>
 * 评分记录Mapper接口
 * </p>
 */
@Mapper
public interface RateRecordMapper extends BaseMapper<RateRecord> {

}
