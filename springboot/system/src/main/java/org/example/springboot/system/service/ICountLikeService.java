package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.CountLikeDto;
import org.example.springboot.system.domain.entity.CountLike;
import org.example.springboot.system.domain.vo.CountLikeVo;

import java.util.List;

/**
 * <p>
 * 点赞量服务类
 * </p>
 */
public interface ICountLikeService extends IService<CountLike> {
    /**
     * 查询点赞量列表
     *
     * @param dto 点赞量
     * @return 结果
     */
    List<CountLikeVo> getList(CountLikeDto dto);

    /**
     * 查询点赞量分页
     *
     * @param dto 点赞量
     * @return 结果
     */
    IPage<CountLikeVo> getPage(CountLikeDto dto);

    /**
     * 查询点赞量
     *
     * @param dto 点赞量
     * @return 结果
     */
    CountLikeVo getOne(CountLikeDto dto);
}
