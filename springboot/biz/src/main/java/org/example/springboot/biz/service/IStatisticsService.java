package org.example.springboot.biz.service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计服务类
 * </p>
 */
public interface IStatisticsService {
    /**
     * 仪表盘统计
     *
     * @return 结果
     */
    Map<String, Long> getDashboardInfo();

    /**
     * 统计笔记
     *
     * @param year 年份
     * @return 结果
     */
    List<Map<String, Object>> getNote(Integer year);
}
