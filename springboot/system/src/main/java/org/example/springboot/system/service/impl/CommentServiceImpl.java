package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.service.IBaseService;
import org.example.springboot.common.utils.AddressUtils;
import org.example.springboot.common.utils.DataUtils;
import org.example.springboot.common.utils.ExcelUtils;
import org.example.springboot.common.utils.ServletUtils;
import org.example.springboot.system.domain.dto.CommentDto;
import org.example.springboot.system.domain.entity.Comment;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.CommentVo;
import org.example.springboot.system.mapper.CommentMapper;
import org.example.springboot.system.service.ICommentService;
import org.example.springboot.system.service.ICountCommentService;
import org.example.springboot.system.service.IUserService;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论服务实现类
 * </p>
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService, IBaseService<Comment> {
    @Resource
    private IUserService userService;
    @Resource
    private ICountCommentService countCommentService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Transactional
    @Override
    public boolean save(Comment entity) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent ua = ServletUtils.getUserAgent(request);
        String ip = ServletUtils.getUserIp(request);
        String location = AddressUtils.getRealAddressByIP(ip);
        Long userId = UserUtils.getLoginUserId();

        entity.setReplyUserId(getReplyUserIdByReplyId(entity.getReplyId()));
        entity.setUserId(userId);
        entity.setOs(ua.getOs().getName());
        entity.setIp(ip);
        entity.setLocation(location);

        boolean flag = super.save(entity);
        countCommentService.countPlus(entity.getBizId(), entity.getBizType());
        return flag;
    }

    @Transactional
    @Override
    public boolean saveOrUpdate(Comment entity) {
        // 祖级回复ID
        if (entity.getReplyId() == null || entity.getReplyId() == 0L) {
            entity.setReplyId(0L);
            entity.setAncestorId(0L);
        } else {
            Long ancestorId = DataUtils.getAncestorId(entity.getReplyId(), this::getById, Comment::getReplyId);
            entity.setAncestorId(ancestorId);
        }
        if (entity.getId() == null) {
            return save(entity);
        }
        return super.updateById(entity);
    }

    @Override
    public List<CommentVo> getList(CommentDto dto) {
        List<Comment> list = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        // 回复
        // TODO 回复
        // 用户
        List<Long> userIdList = list.stream().map(Comment::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return list.stream().map(item -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<CommentVo> getPage(CommentDto dto) {
        // 祖级
        dto.setAncestorId(0L);
        Page<Comment> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        Page<Comment> info = getWrapper(dto)
                .select(Comment::getId)
                .page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 子级
        List<Long> idList = info.getRecords().stream().map(Comment::getId).toList();
        List<Comment> commentList = lambdaQuery()
                .in(Comment::getId, idList)
                .or()
                .in(Comment::getAncestorId, idList)
                .list();
        // 用户
        List<Long> userIdList = commentList.stream().map(Comment::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        List<CommentVo> vos = commentList.stream().map(item -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().build()));
            return vo;
        }).toList();
        // 树
        List<CommentVo> tree = DataUtils.listToTree(
                vos,
                CommentVo::getAncestorId,
                CommentVo::setChildren,
                CommentVo::getId,
                0L,
                CommentVo::getCreateTime,
                Comparator.naturalOrder()
        );
        // 组装VO
        IPage<CommentVo> convert = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        convert.setRecords(tree);
        return convert;
    }

    @Override
    public CommentVo getOne(CommentDto dto) {
        Comment one = getWrapper(dto).one();
        if (one == null) {
            return null;
        }
        // 回复
        // TODO 回复
        // 用户
        User user = Optional.ofNullable(userService.getById(one.getUserId())).orElse(User.builder().name("已删除").build());
        // 组装VO
        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(one, vo);
        vo.setUser(user);
        return vo;
    }

    @Override
    public void exportExcel(Comment entity, HttpServletResponse response) {
        ExcelUtils.exportExcel(response, this, entity, Comment.class, threadPoolTaskExecutor);

    }

    @Override
    public List<Comment> getPageList(Comment entity, IPage<Comment> page) {
        IPage<Comment> info = getWrapper(entity).page(page);
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return List.of();
        }
        return info.getRecords();
    }

    @Override
    public LambdaQueryChainWrapper<Comment> getWrapper(Comment entity) {
        LambdaQueryChainWrapper<Comment> wrapper = lambdaQuery()
                .eq(entity.getId() != null, Comment::getId, entity.getId())
                .eq(entity.getBizId() != null, Comment::getBizId, entity.getBizId())
                .eq(entity.getBizType() != null, Comment::getBizType, entity.getBizType())
                .like(StrUtil.isNotBlank(entity.getContent()), Comment::getContent, entity.getContent())
                .eq(entity.getReplyId() != null, Comment::getReplyId, entity.getReplyId())
                .eq(entity.getUserId() != null, Comment::getUserId, entity.getUserId())
                .like(StrUtil.isNotBlank(entity.getOs()), Comment::getOs, entity.getOs())
                .like(StrUtil.isNotBlank(entity.getIp()), Comment::getIp, entity.getIp())
                .like(StrUtil.isNotBlank(entity.getLocation()), Comment::getLocation, entity.getLocation());
        if (entity instanceof CommentDto dto) {
            Map<String, Object> params = dto.getParams();
            // 创建时间
            Object startCreateTime = params == null ? null : params.get("startCreateTime");
            Object endCreateTime = params == null ? null : params.get("endCreateTime");

            wrapper.between(ObjectUtil.isAllNotEmpty(startCreateTime, endCreateTime),
                    Comment::getCreateTime,
                    startCreateTime, endCreateTime);
        }
        return wrapper;
    }

    /**
     * 根据回复ID获取回复用户ID
     *
     * @param replyId 回复ID
     * @return 结果
     */
    private Long getReplyUserIdByReplyId(Long replyId) {
        if (replyId == null || replyId == 0L) {
            return 0L;
        }
        Comment reply = this.getById(replyId);
        return reply != null ? reply.getUserId() : 0L;
    }
}
