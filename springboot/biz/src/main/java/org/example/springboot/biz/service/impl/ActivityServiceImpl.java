package org.example.springboot.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.ActivityDto;
import org.example.springboot.biz.domain.entity.Activity;
import org.example.springboot.biz.domain.entity.ActivityCategory;
import org.example.springboot.biz.domain.vo.ActivityVo;
import org.example.springboot.biz.mapper.ActivityMapper;
import org.example.springboot.biz.service.IActivityCategoryService;
import org.example.springboot.biz.service.IActivityService;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.BizType;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.vo.CountVo;
import org.example.springboot.system.domain.vo.FavoriteCountVo;
import org.example.springboot.system.domain.vo.LikeCountVo;
import org.example.springboot.system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动服务实现类
 * </p>
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService, IBaseService<Activity> {
    @Resource
    private IActivityCategoryService activityCategoryService;
    @Resource
    private ICountViewService countViewService;
    @Resource
    private ICountLikeService countLikeService;
    @Resource
    private ICountDislikeService countDislikeService;
    @Resource
    private ICountCommentService countCommentService;
    @Resource
    private ICountFavoriteService countFavoriteService;
    @Resource
    private IAttachmentService attachmentService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<ActivityVo> getList(ActivityDto dto) {
        List<Activity> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 类别
        List<Long> categoryIdList = list.stream().map(Activity::getCategoryId).toList();
        List<ActivityCategory> categoryList = activityCategoryService.listByIds(categoryIdList);
        Map<Long, ActivityCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(ActivityCategory::getId, item -> item));
        // ID列表
        List<Long> idList = list.stream().map(Activity::getId).toList();
        // 活动附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 浏览量
        Map<Long, Long> viewCountMap = countViewService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 点赞量
        Map<Long, LikeCountVo> countLikeVoMap = countLikeService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 收藏量
        Map<Long, FavoriteCountVo> countFavoriteVoMap = countFavoriteService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 组装VO
        return list.stream().map(item -> {
            ActivityVo vo = new ActivityVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), ActivityCategory.builder().name("已删除").build()));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            vo.setCount(CountVo.builder()
                    .view(viewCountMap.getOrDefault(item.getId(), 0L))
                    .like(countLikeVoMap.getOrDefault(item.getId(), LikeCountVo.builder().hasDone(false).num(0L).build()))
                    .dislike(dislikeCountMap.getOrDefault(item.getId(), 0L))
                    .comment(commentCountMap.getOrDefault(item.getId(), 0L))
                    .favorite(countFavoriteVoMap.getOrDefault(item.getId(), FavoriteCountVo.builder().hasDone(false).num(0L).build()))
                    .build());
            return vo;
        }).toList();
    }

    @Override
    public IPage<ActivityVo> getPage(ActivityDto dto) {
        Page<Activity> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 类别
        List<Long> categoryIdList = info.getRecords().stream().map(Activity::getCategoryId).toList();
        List<ActivityCategory> categoryList = activityCategoryService.listByIds(categoryIdList);
        Map<Long, ActivityCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(ActivityCategory::getId, item -> item));
        // ID列表
        List<Long> idList = info.getRecords().stream().map(Activity::getId).toList();
        // 活动附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 浏览量
        Map<Long, Long> viewCountMap = countViewService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 点赞量
        Map<Long, LikeCountVo> countLikeVoMap = countLikeService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 收藏量
        Map<Long, FavoriteCountVo> countFavoriteVoMap = countFavoriteService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_ACTIVITY.getCode());
        // 组装VO
        return info.convert(item -> {
            ActivityVo vo = new ActivityVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), ActivityCategory.builder().name("已删除").build()));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            vo.setCount(CountVo.builder()
                    .view(viewCountMap.getOrDefault(item.getId(), 0L))
                    .like(countLikeVoMap.getOrDefault(item.getId(), LikeCountVo.builder().hasDone(false).num(0L).build()))
                    .dislike(dislikeCountMap.getOrDefault(item.getId(), 0L))
                    .comment(commentCountMap.getOrDefault(item.getId(), 0L))
                    .favorite(countFavoriteVoMap.getOrDefault(item.getId(), FavoriteCountVo.builder().hasDone(false).num(0L).build()))
                    .build());
            return vo;
        });
    }

    @Override
    public ActivityVo getOne(ActivityDto dto) {
        Activity one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // ID
        Long id = one.getId();
        // 访问量
        countViewService.countPlus(id, BizType.BIZ_ACTIVITY.getCode());
        // 类别
        ActivityCategory category = Optional.ofNullable(activityCategoryService.getById(one.getCategoryId())).orElse(ActivityCategory.builder().name("已删除").build());
        // 活动附件
        List<Attachment> attachmentList = attachmentService.listByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 浏览量
        Long viewCount = countViewService.getCountByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 点赞量
        LikeCountVo like = countLikeService.getCountVoByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 点踩量
        Long dislikeCount = countDislikeService.getCountByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 评论量
        Long commentCount = countCommentService.getCountByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 收藏量
        FavoriteCountVo favorite = countFavoriteService.getCountVoByBizIdAndBizType(id, BizType.BIZ_ACTIVITY.getCode());
        // 组装VO
        ActivityVo vo = new ActivityVo();
        BeanUtils.copyProperties(one, vo);
        vo.setCategory(category);
        vo.setAttachmentList(attachmentList);
        vo.setCount(CountVo.builder()
                .view(viewCount)
                .like(like)
                .dislike(dislikeCount)
                .comment(commentCount)
                .favorite(favorite)
                .build());
        return vo;
    }

    @Override
    public void exportExcel(Activity entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Activity.class, threadPoolTaskExecutor);
    }

    @Override
    public List<Activity> getPageList(Activity entity, IPage<Activity> page) {
        IPage<Activity> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Activity> getWrapper(Activity entity) {
        LambdaQueryChainWrapper<Activity> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Activity::getId, entity.getId())
                .like(StrUtil.isNotBlank(entity.getTitle()), Activity::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), Activity::getContent, entity.getContent())
                .eq(entity.getCategoryId() != null, Activity::getCategoryId, entity.getCategoryId())
                .like(StrUtil.isNotBlank(entity.getLocation()), Activity::getLocation, entity.getLocation());
        if (entity instanceof ActivityDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");
            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Activity::getCreateTime,
                    startCreateTime, endCreateTime);
            // 活动时间
            Object startActivityDateTime = params == null ? null : params.get("startActivityDateTime");
            Object endActivityDateTime = params == null ? null : params.get("endActivityDateTime");
            if (ObjectUtil.isAllNotEmpty(startActivityDateTime, endActivityDateTime)) {
                wrapper.ge(Activity::getStartDatetime, startActivityDateTime)
                        .le(Activity::getEndDatetime, endActivityDateTime);
            }
            // 排序
            String orderBy = dto.getOrderBy();
            Boolean isAsc = dto.getIsAsc();
            if (StrUtil.isNotBlank(orderBy) && isAsc != null) {
                wrapper.orderBy(Objects.equals(orderBy, "createTime"), isAsc, Activity::getCreateTime);
            }
        }
        return wrapper;
    }
}
