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
import org.example.springboot.biz.common.enums.NoteStatus;
import org.example.springboot.biz.domain.dto.NoteDto;
import org.example.springboot.biz.domain.entity.Note;
import org.example.springboot.biz.domain.entity.NoteCategory;
import org.example.springboot.biz.domain.vo.NoteVo;
import org.example.springboot.biz.mapper.NoteMapper;
import org.example.springboot.biz.service.INoteCategoryService;
import org.example.springboot.biz.service.INoteService;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.system.common.enums.BizType;
import org.example.springboot.system.common.enums.DeleteEnum;
import org.example.springboot.system.domain.dto.CountDto;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.service.*;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 笔记服务实现类
 * </p>
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService, IBaseService<Note> {
    @Resource
    private IUserService userService;
    @Resource
    private INoteCategoryService noteCategoryService;
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
    public boolean save(Note entity) {
        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        entity.setStatus(NoteStatus.PUBLISHED.getCode());
        entity.setDeleted(DeleteEnum.NORMAL.getCode());
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(Note entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<NoteVo> getList(NoteDto dto) {
        List<Note> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = list.stream().map(Note::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 类别
        List<Long> categoryIdList = list.stream().map(Note::getCategoryId).toList();
        List<NoteCategory> categoryList = noteCategoryService.listByIds(categoryIdList);
        Map<Long, NoteCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(NoteCategory::getId, item -> item));
        // ID列表
        List<Long> idList = list.stream().map(Note::getId).toList();
        // 笔记附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 浏览量
        Map<Long, Long> viewCountMap = countViewService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点赞量
        Map<Long, Long> likeCountMap = countLikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 收藏量
        Map<Long, Long> favoriteCountMap = countFavoriteService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 组装VO
        return list.stream().map(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            vo.setCount(CountDto.builder()
                    .view(viewCountMap.getOrDefault(item.getId(), 0L))
                    .like(likeCountMap.getOrDefault(item.getId(), 0L))
                    .dislike(dislikeCountMap.getOrDefault(item.getId(), 0L))
                    .comment(commentCountMap.getOrDefault(item.getId(), 0L))
                    .favorite(favoriteCountMap.getOrDefault(item.getId(), 0L))
                    .build());
            return vo;
        }).toList();
    }

    @Override
    public IPage<NoteVo> getPage(NoteDto dto) {
        Page<Note> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(Note::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 类别
        List<Long> categoryIdList = info.getRecords().stream().map(Note::getCategoryId).toList();
        List<NoteCategory> categoryList = noteCategoryService.listByIds(categoryIdList);
        Map<Long, NoteCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(NoteCategory::getId, item -> item));
        // ID列表
        List<Long> idList = info.getRecords().stream().map(Note::getId).toList();
        // 笔记附件
        Map<Long, List<Attachment>> attachmentMap = attachmentService.groupByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 浏览量
        Map<Long, Long> viewCountMap = countViewService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点赞量
        Map<Long, Long> likeCountMap = countLikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 收藏量
        Map<Long, Long> favoriteCountMap = countFavoriteService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 组装VO
        return info.convert(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
            vo.setAttachmentList(attachmentMap.getOrDefault(item.getId(), List.of()));
            vo.setCount(CountDto.builder()
                    .view(viewCountMap.getOrDefault(item.getId(), 0L))
                    .like(likeCountMap.getOrDefault(item.getId(), 0L))
                    .dislike(dislikeCountMap.getOrDefault(item.getId(), 0L))
                    .comment(commentCountMap.getOrDefault(item.getId(), 0L))
                    .favorite(favoriteCountMap.getOrDefault(item.getId(), 0L))
                    .build());
            return vo;
        });
    }

    @Override
    public NoteVo getOne(NoteDto dto) {
        Note one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 类别
        NoteCategory category = Optional.ofNullable(noteCategoryService.getById(one.getCategoryId())).orElse(NoteCategory.builder().name("已删除").build());
        // ID
        Long id = one.getId();
        // 笔记附件
        List<Attachment> attachmentList = attachmentService.listByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 浏览量
        Long viewCount = countViewService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 点赞量
        Long likeCount = countLikeService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Long dislikeCount = countDislikeService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 评论量
        Long commentCount = countCommentService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 收藏量
        Long favoriteCount = countFavoriteService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 组装VO
        NoteVo vo = new NoteVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setCategory(category);
        vo.setAttachmentList(attachmentList);
        vo.setCount(CountDto.builder()
                .view(viewCount)
                .like(likeCount)
                .dislike(dislikeCount)
                .comment(commentCount)
                .favorite(favoriteCount)
                .build());
        return vo;
    }

    @Override
    public void exportExcel(Note entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Note.class, threadPoolTaskExecutor);
    }

    @Override
    public void handleTop(Long id) {
        Note note = getById(id);
        if (note == null) {
            throw new ServiceException(ResultCode.NOTE_NOT_FOUND_ERROR);
        }
        note.setTop(!note.getTop());
        updateById(note);
    }

    @Override
    public void handleComment(Long id) {
        Note note = getById(id);
        if (note == null) {
            throw new ServiceException(ResultCode.NOTE_NOT_FOUND_ERROR);
        }
        note.setCommentable(!note.getCommentable());
        updateById(note);
    }

    @Override
    public List<Note> getPageList(Note entity, IPage<Note> page) {
        IPage<Note> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Note> getWrapper(Note entity) {
        LambdaQueryChainWrapper<Note> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Note::getId, entity.getId())
                .eq(entity.getUserId() != null, Note::getUserId, entity.getUserId())
                .like(StrUtil.isNotBlank(entity.getTitle()), Note::getTitle, entity.getTitle())
                .like(StrUtil.isNotBlank(entity.getContent()), Note::getContent, entity.getContent())
                .eq(entity.getCategoryId() != null, Note::getCategoryId, entity.getCategoryId())
                .eq(entity.getTop() != null, Note::getTop, entity.getTop())
                .eq(entity.getVisible() != null, Note::getVisible, entity.getVisible())
                .eq(entity.getCommentable() != null, Note::getCommentable, entity.getCommentable())
                .eq(entity.getStatus() != null, Note::getStatus, entity.getStatus())
                .eq(entity.getDeleted() != null, Note::getDeleted, entity.getDeleted());
        if (entity instanceof NoteDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Note::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }
}
