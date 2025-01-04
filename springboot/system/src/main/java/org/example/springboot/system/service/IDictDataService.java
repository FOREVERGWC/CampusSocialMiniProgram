package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.DictDataDto;
import org.example.springboot.system.domain.entity.DictData;
import org.example.springboot.system.domain.vo.DictDataVo;

import java.util.List;

/**
 * <p>
 * 字典数据服务类
 * </p>
 */
public interface IDictDataService extends IService<DictData> {
    /**
     * 查询字典数据列表
     *
     * @param dto 字典数据
     * @return 结果
     */
    List<DictDataVo> getList(DictDataDto dto);

    /**
     * 查询字典数据分页
     *
     * @param dto 字典数据
     * @return 结果
     */
    IPage<DictDataVo> getPage(DictDataDto dto);

    /**
     * 查询字典数据
     *
     * @param dto 字典数据
     * @return 结果
     */
    DictDataVo getOne(DictDataDto dto);

    /**
     * 导出字典数据
     *
     * @param entity   字典数据
     * @param response 响应对象
     */
    void exportExcel(DictData entity, HttpServletResponse response);

    /**
     * 恢复或停用字典数据
     *
     * @param id 字典数据ID
     */
    void handleStatus(Long id);
}
