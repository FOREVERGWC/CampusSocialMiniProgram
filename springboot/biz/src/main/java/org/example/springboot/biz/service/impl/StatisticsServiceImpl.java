package org.example.springboot.biz.service.impl;

import jakarta.annotation.Resource;
import org.example.springboot.biz.service.INoteService;
import org.example.springboot.biz.service.IStatisticsService;
import org.example.springboot.system.domain.entity.CountView;
import org.example.springboot.system.service.ICommentService;
import org.example.springboot.system.service.ICountViewService;
import org.example.springboot.system.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 统计服务实现类
 * </p>
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private INoteService noteService;
    @Resource
    private IUserService userService;
    @Resource
    private ICommentService commentService;
    @Resource
    private ICountViewService countViewService;

    @Override
    public Map<String, Long> getDashboardInfo() {

        Map<String, Long> map = new LinkedHashMap<>();
        long noteCount = noteService.count();
        long userCount = userService.count();
        long commentCount = commentService.count();
        Long viewCount = Optional.ofNullable(countViewService.list())
                .orElse(List.of())
                .stream()
                .map(CountView::getCount)
                .mapToLong(Long::longValue)
                .sum();
        map.put("noteCount", noteCount);
        map.put("userCount", userCount);
        map.put("commentCount", commentCount);
        map.put("viewCount", viewCount);
        return map;
    }

    @Override
    public List<Map<String, Object>> getNote(Integer year) {
        // 构建查询条件
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (year != null) {
//            queryWrapper.ge(Article::getCreateTime, LocalDateTime.of(year, 1, 1, 0, 0))
//                    .lt(Article::getCreateTime, LocalDateTime.of(year + 1, 1, 1, 0, 0));
//        }
        // 查询数据
//        List<Article> articleList = articleService.list(queryWrapper);
//        List<Article> articleList = List.of();
        // 初始化每个月的数据为0
        Map<Integer, Integer> monthlyCount = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyCount.put(i, 0);
        }
        // 填充实际数据
//        for (Article article : articleList) {
//            int month = article.getCreateTime().get(ChronoField.MONTH_OF_YEAR);
//            monthlyCount.put(month, monthlyCount.get(month) + 1);
//        }
        // 转换为前端需要的格式
        return monthlyCount.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
