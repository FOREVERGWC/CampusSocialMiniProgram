package org.example.springboot.system.service;

import org.example.springboot.system.domain.entity.Dislike;

/**
 * <p>
 * 点踩服务类
 * </p>
 */
public interface IDislikeService {
    /**
     * 点踩或取消点踩
     *
     * @param dislike 点踩
     */
    void handleDislike(Dislike dislike);
}
