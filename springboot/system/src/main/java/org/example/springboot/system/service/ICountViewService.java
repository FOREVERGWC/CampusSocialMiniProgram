package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountViewDto;
import org.example.springboot.system.domain.entity.CountView;
import org.example.springboot.system.domain.vo.CountViewVo;

import java.util.List;

/**
 * <p>
 * 浏览量服务类
 * </p>
 */
public interface ICountViewService extends IService<CountView> {
    /**
     * 查询浏览量列表
     *
     * @param dto 浏览量
     * @return 结果
     */
    List<CountViewVo> getList(CountViewDto dto);

    /**
     * 查询浏览量分页
     *
     * @param dto 浏览量
     * @return 结果
     */
    IPage<CountViewVo> getPage(CountViewDto dto);

    /**
     * 查询浏览量
     *
     * @param dto 浏览量
     * @return 结果
     */
    CountViewVo getOne(CountViewDto dto);
}
