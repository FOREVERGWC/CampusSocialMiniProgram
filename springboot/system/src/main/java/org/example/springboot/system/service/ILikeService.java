package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.springboot.system.domain.dto.LikeDto;
import org.example.springboot.system.domain.entity.Like;

/**
 * <p>
 * 点赞服务类
 * </p>
 */
public interface ILikeService {
    /**
     * 查询我的点赞分页
     *
     * @param dto 点赞
     * @return 结果
     */
    IPage<Like> getMyPage(LikeDto dto);

    /**
     * 点赞或取消点赞
     *
     * @param like 点赞
     * @return 结果
     */
    Long handleLike(Like like);
}
