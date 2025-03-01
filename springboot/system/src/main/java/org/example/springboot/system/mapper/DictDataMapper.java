package org.example.springboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.system.domain.entity.DictData;
import org.example.springboot.system.domain.vo.DictDataVo;

import java.util.List;

/**
 * <p>
 * 字典数据Mapper接口
 * </p>
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {
    /**
     * 根据表名查询字典数据列表
     *
     * @param tableName 表名
     * @param label     键
     * @param value     值
     * @return 结果
     */
    List<DictDataVo> selectListByTableName(@Param("tableName") String tableName, @Param("label") String label, @Param("value") String value);
}
