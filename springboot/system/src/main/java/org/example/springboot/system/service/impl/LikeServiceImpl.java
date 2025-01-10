package org.example.springboot.system.service.impl;

import org.example.springboot.system.domain.entity.Like;
import org.example.springboot.system.service.ILikeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞服务实现类
 * </p>
 */
@Service
public class LikeServiceImpl implements ILikeService {
    @Override
    public void handleLike(Like like) {
        // TODO: Redis
    }
}
