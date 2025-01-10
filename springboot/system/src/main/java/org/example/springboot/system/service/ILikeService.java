package org.example.springboot.system.service;

import org.example.springboot.system.domain.entity.Like;

/**
 * <p>
 * 点赞服务类
 * </p>
 */
public interface ILikeService {
    /**
     * 点赞或取消点赞
     *
     * @param like 点赞
     */
    void handleLike(Like like);
}
