package org.example.springboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.springboot.system.domain.dto.CommentDto;
import org.example.springboot.system.domain.entity.Comment;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.CommentVo;
import org.example.springboot.system.mapper.CommentMapper;
import org.example.springboot.system.service.ICommentService;
import org.example.springboot.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private IUserService userService;

    @Override
    public List<CommentVo> getList(CommentDto dto) {
        List<Comment> commentList = getWrapper(dto).list();
        if (CollectionUtil.isEmpty(commentList)) {
            return List.of();
        }
        // 回复
        // TODO 回复
        // 用户
        List<Long> userIdList = commentList.stream().map(Comment::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return commentList.stream().map(item -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        }).toList();
    }

    @Override
    public IPage<CommentVo> getPage(CommentDto dto) {
        Page<Comment> info = getWrapper(dto).page(new Page<>(dto.getPageNo(), dto.getPageSize()));
        if (CollectionUtil.isEmpty(info.getRecords())) {
            return new Page<>(dto.getPageNo(), dto.getPageSize(), 0);
        }
        // 回复
        // TODO 回复
        // 用户
        List<Long> userIdList = info.getRecords().stream().map(Comment::getUserId).toList();
        List<User> userList = userService.listByIds(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
        // 组装VO
        return info.convert(item -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUser(userMap.getOrDefault(item.getUserId(), User.builder().name("已删除").build()));
            return vo;
        });
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

    /**
     * 组装查询包装器
     *
     * @param entity 评论
     * @return 结果
     */
    private LambdaQueryChainWrapper<Comment> getWrapper(Comment entity) {
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
}
