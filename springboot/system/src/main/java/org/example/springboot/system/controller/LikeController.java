package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.LikeDto;
import org.example.springboot.system.domain.entity.Like;
import org.example.springboot.system.service.ILikeService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 点赞前端控制器
 * </p>
 */
@RestController
@RequestMapping("/like")
@Tag(name = "点赞", description = "点赞")
public class LikeController {
    @Resource
    private ILikeService likeService;

    /**
     * 查询我的点赞分页
     *
     * @param dto 点赞
     * @return 结果
     */
    @GetMapping("/my")
    @Operation(summary = "查询我的点赞分页", description = "查询我的点赞分页", method = "GET")
    public Result<IPage<Like>> getMyPage(LikeDto dto) {
        IPage<Like> page = likeService.getMyPage(dto);
        return Result.success(page);
    }

    /**
     * 点赞或取消点赞
     *
     * @param like 点赞
     * @return 结果
     */
    @PutMapping
    @Operation(summary = "点赞或取消点赞", description = "点赞或取消点赞", method = "PUT")
    public Result<Long> handleLike(@RequestBody Like like) {
        Long count = likeService.handleLike(like);
        return Result.success(count);
    }
}
