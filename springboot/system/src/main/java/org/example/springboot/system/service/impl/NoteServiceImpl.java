package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.NoteDto;
import org.example.springboot.system.domain.entity.Note;
import org.example.springboot.system.domain.entity.NoteCategory;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.NoteVo;
import org.example.springboot.system.mapper.NoteMapper;
import org.example.springboot.system.service.INoteCategoryService;
import org.example.springboot.system.service.INoteService;
import org.example.springboot.system.service.IUserService;
import org.springframework.beans.BeanUtils;
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
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {
    @Resource
    private IUserService userService;
    @Resource
    private INoteCategoryService noteCategoryService;

    @Override
    public List<NoteVo> getList(NoteDto dto) {
        List<Note> noteList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(noteList)) {
            return List.of();
        }
        // 用户
        List<Long> userIdList = noteList.stream().map(Note::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 类别
        List<Long> categoryIdList = noteList.stream().map(Note::getCategoryId).toList();
        List<NoteCategory> categoryList = noteCategoryService.listByIds(categoryIdList);
        Map<Long, NoteCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(NoteCategory::getId, item -> item));
        // 组装VO
        return noteList.stream().map(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
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
        // 组装VO
        return info.convert(item -> {
            NoteVo vo = new NoteVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            vo.setCategory(categoryMap.getOrDefault(item.getCategoryId(), NoteCategory.builder().name("已删除").build()));
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
        // 组装VO
        NoteVo vo = new NoteVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        vo.setCategory(category);
        return vo;
    }

    /**
     * 组装查询包装器
     *
     * @param entity 笔记
     * @return 结果
     */
    private LambdaQueryChainWrapper<Note> getWrapper(Note entity) {
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
