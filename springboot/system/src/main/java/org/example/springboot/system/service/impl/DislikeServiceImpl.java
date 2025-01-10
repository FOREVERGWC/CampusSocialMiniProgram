package org.example.springboot.system.service.impl;

import org.example.springboot.system.domain.entity.Dislike;
import org.example.springboot.system.service.IDislikeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点踩服务实现类
 * </p>
 */
@Service
public class DislikeServiceImpl implements IDislikeService {
    @Override
    public void handleDislike(Dislike dislike) {
        // TODO: Redis
    }
}
