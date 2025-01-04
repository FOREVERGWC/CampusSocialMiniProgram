package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.springboot.system.domain.dto.FollowDto;
import org.example.springboot.system.domain.entity.Follow;
import org.example.springboot.system.domain.vo.FollowVo;

import java.util.List;

/**
 * <p>
 * 关注服务类
 * </p>
 */
public interface IFollowService extends IService<Follow> {
    /**
     * 查询关注列表
     *
     * @param dto 关注
     * @return 结果
     */
    List<FollowVo> getList(FollowDto dto);

    /**
     * 查询关注分页
     *
     * @param dto 关注
     * @return 结果
     */
    IPage<FollowVo> getPage(FollowDto dto);

    /**
     * 查询关注
     *
     * @param dto 关注
     * @return 结果
     */
    FollowVo getOne(FollowDto dto);
}
