package org.example.springboot.biz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.biz.service.IStatisticsService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计前端控制器
 * </p>
 */
@RestController
@RequestMapping("/statistics")
@Tag(name = "统计", description = "统计")
public class StatisticsController {
    @Resource
    private IStatisticsService statisticsService;

    /**
     * 仪表盘统计
     *
     * @return 结果
     */
    @GetMapping("/dashboard/info")
    @Operation(summary = "仪表盘统计", description = "仪表盘统计", method = "GET")
    public Result<Map<String, Long>> getDashboardInfo() {
        Map<String, Long> dashboardInfo = statisticsService.getDashboardInfo();
        return Result.success(dashboardInfo);
    }

    /**
     * 统计笔记
     *
     * @param year 年份
     * @return 结果
     */
    @GetMapping("/note")
    @Operation(summary = "统计笔记", description = "统计笔记", method = "GET")
    public Result<List<Map<String, Object>>> getNote(@RequestParam(required = false) Integer year) {
        List<Map<String, Object>> list = statisticsService.getNote(year);
        return Result.success(list);
    }
}
