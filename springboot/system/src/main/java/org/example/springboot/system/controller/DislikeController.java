package org.example.springboot.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.entity.Dislike;
import org.example.springboot.system.service.IDislikeService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 点踩前端控制器
 * </p>
 */
@RestController
@RequestMapping("/dislike")
@Tag(name = "点踩", description = "点踩")
public class DislikeController {
    @Resource
    private IDislikeService dislikeService;

    /**
     * 点踩或取消点踩
     *
     * @param dislike 点踩
     * @return 结果
     */
    @PutMapping
    @Operation(summary = "点踩或取消点踩", description = "点踩或取消点踩", method = "PUT")
    public Result<Void> handleDislike(@RequestBody Dislike dislike) {
        dislikeService.handleDislike(dislike);
        return Result.success();
    }
}
