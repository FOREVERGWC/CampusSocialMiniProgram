package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountDislikeDto;
import org.example.springboot.system.domain.entity.CountDislike;
import org.example.springboot.system.domain.vo.CountDislikeVo;

import java.util.List;

/**
 * <p>
 * 点踩量服务类
 * </p>
 */
public interface ICountDislikeService extends IService<CountDislike> {
    /**
     * 查询点踩量列表
     *
     * @param dto 点踩量
     * @return 结果
     */
    List<CountDislikeVo> getList(CountDislikeDto dto);

    /**
     * 查询点踩量分页
     *
     * @param dto 点踩量
     * @return 结果
     */
    IPage<CountDislikeVo> getPage(CountDislikeDto dto);

    /**
     * 查询点踩量
     *
     * @param dto 点踩量
     * @return 结果
     */
    CountDislikeVo getOne(CountDislikeDto dto);
}
