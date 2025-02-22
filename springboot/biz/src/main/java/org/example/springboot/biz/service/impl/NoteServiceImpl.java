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
import org.example.springboot.system.domain.dto.FavoriteDto;
import org.example.springboot.system.domain.dto.LikeDto;
import org.example.springboot.system.domain.entity.Attachment;
import org.example.springboot.system.domain.entity.Like;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.CountVo;
import org.example.springboot.system.domain.vo.FavoriteCountVo;
import org.example.springboot.system.domain.vo.FavoriteVo;
import org.example.springboot.system.domain.vo.LikeCountVo;
import org.example.springboot.system.service.*;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private ILikeService likeService;
    @Resource
    private IFavoriteService favoriteService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public boolean save(Note entity) {
        Long userId = UserUtils.getLoginUserId();
        entity.setUserId(userId);
        entity.setStatus(NoteStatus.UNPUBLISHED.getCode());
        entity.setDeleted(DeleteEnum.NORMAL.getCode());

        NoteVo one = getOne(NoteDto.builder()
                .userId(userId)
                .status(NoteStatus.UNPUBLISHED.getCode())
                .build());

        if (one == null) {
            return super.save(entity);
        }

        entity.setId(one.getId());
        entity.setTitle(StrUtil.isBlank(entity.getTitle()) ? one.getTitle() : entity.getTitle());
        entity.setContent(StrUtil.isBlank(entity.getContent()) ? one.getContent() : entity.getContent());
        entity.setCategoryId(entity.getCategoryId() == null ? one.getCategoryId() : entity.getCategoryId());
        return super.updateById(entity);
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
        Map<Long, LikeCountVo> countLikeVoMap = countLikeService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 收藏量
        Map<Long, FavoriteCountVo> countFavoriteVoMap = countFavoriteService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 组装VO
        return list.stream().map(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
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
        Map<Long, LikeCountVo> countLikeVoMap = countLikeService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Map<Long, Long> dislikeCountMap = countDislikeService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 评论量
        Map<Long, Long> commentCountMap = countCommentService.mapCountByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 收藏量
        Map<Long, FavoriteCountVo> countFavoriteVoMap = countFavoriteService.mapCountVoByBizIdsAndBizType(idList, BizType.BIZ_NOTE.getCode());
        // 组装VO
        return info.convert(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
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
    public IPage<NoteVo> getMyPage(NoteDto dto) {
        Long userId = UserUtils.getLoginUserId();
        dto.setUserId(userId);
        return getPage(dto);
    }

    @Override
    public IPage<NoteVo> getMyLikePage(NoteDto dto) {
        LikeDto like = LikeDto.builder()
                .pageNo(dto.getPageNo())
                .pageSize(dto.getPageSize())
                .bizType(BizType.BIZ_NOTE.getCode())
                .build();
        IPage<Like> info = likeService.getMyPage(like);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // ID列表
        List<Long> idList = info.getRecords().stream().map(Like::getBizId).toList();
        dto.setIdList(idList);
        List<NoteVo> list = getList(dto);
        if (CollectionUtil.isEmpty(list)) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        IPage<NoteVo> page = new Page<>(info.getCurrent(), info.getSize(), info.getTotal());
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<NoteVo> getMyFavoritePage(NoteDto dto) {
        FavoriteDto favorite = FavoriteDto.builder()
                .pageNo(dto.getPageNo())
                .pageSize(dto.getPageSize())
                .bizType(BizType.BIZ_NOTE.getCode())
                .build();
        IPage<FavoriteVo> info = favoriteService.getMyPage(favorite);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // ID列表
        List<Long> idList = info.getRecords().stream().map(FavoriteVo::getBizId).toList();
        dto.setIdList(idList);
        List<NoteVo> list = getList(dto);
        if (CollectionUtil.isEmpty(list)) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 组装VO
        IPage<NoteVo> page = new Page<>(info.getCurrent(), info.getSize(), info.getTotal());
        page.setRecords(list);
        return page;
    }

    @Override
    public NoteVo getOne(NoteDto dto) {
        Note one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // ID
        Long id = one.getId();
        // 访问量
        countViewService.countPlus(id, BizType.BIZ_NOTE.getCode());
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 类别
        NoteCategory category = Optional.ofNullable(noteCategoryService.getById(one.getCategoryId())).orElse(NoteCategory.builder().name("已删除").build());
        // 笔记附件
        List<Attachment> attachmentList = attachmentService.listByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 浏览量
        Long viewCount = countViewService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 点赞量
        LikeCountVo like = countLikeService.getCountVoByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 点踩量
        Long dislikeCount = countDislikeService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 评论量
        Long commentCount = countCommentService.getCountByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 收藏量
        FavoriteCountVo favorite = countFavoriteService.getCountVoByBizIdAndBizType(id, BizType.BIZ_NOTE.getCode());
        // 组装VO
        NoteVo vo = new NoteVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
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
    public Map<String, Long> countMyVisible() {
        Map<String, Long> countMap = Stream.of("0", "1")
                .collect(Collectors.toMap(
                        key -> key,
                        key -> 0L
                ));

        Long userId = UserUtils.getLoginUserId();

        List<Note> list = lambdaQuery()
                .eq(Note::getUserId, userId)
                .list();

        if (CollectionUtil.isEmpty(list)) {
            return countMap;
        }

        list.stream()
                .map(Note::getVisible)
                .forEach(visible -> countMap.merge(visible, 1L, Long::sum));

        return countMap;
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
                .eq(StrUtil.isNotBlank(entity.getStatus()), Note::getStatus, entity.getStatus())
                .eq(entity.getDeleted() != null, Note::getDeleted, entity.getDeleted());
        if (entity instanceof NoteDto dto) {
            Map<String, Object> params = dto.getParams();
            // ID列表
            wrapper.in(CollectionUtil.isNotEmpty(dto.getIdList()), Note::getId, dto.getIdList());
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");
            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Note::getCreateTime,
                    startCreateTime, endCreateTime);
            // 排序
            String orderBy = dto.getOrderBy();
            Boolean isAsc = dto.getIsAsc();
            if (StrUtil.isNotBlank(orderBy) && isAsc != null) {
                wrapper.orderBy(Objects.equals(orderBy, "createTime"), isAsc, Note::getCreateTime);
            }
        }
        return wrapper;
    }
}
