package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.UserDto;
import org.example.springboot.system.domain.dto.UserEditDto;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息服务类
 * </p>
 */
public interface IUserService extends IService<User> {
    /**
     * 查询用户信息列表
     *
     * @param dto 用户信息
     * @return 结果
     */
    List<UserVo> getList(UserDto dto);

    /**
     * 查询用户信息分页
     *
     * @param dto 用户信息
     * @return 结果
     */
    IPage<UserVo> getPage(UserDto dto);

    /**
     * 查询用户信息
     *
     * @param dto 用户信息
     * @return 结果
     */
    UserVo getOne(UserDto dto);

    /**
     * 导出用户
     *
     * @param entity   用户
     * @param response 响应对象
     */
    void exportExcel(User entity, HttpServletResponse response);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    User getByUsername(String username);

    /**
     * 根据电话查询用户信息
     *
     * @param phone 电话
     * @return 结果
     */
    User getByPhone(String phone);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 结果
     */
    User getByEmail(String email);

    /**
     * 解禁或禁用用户
     *
     * @param id 用户ID
     */
    void handleStatus(Long id);

    /**
     * 重置密码
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

    /**
     * 根据用户ID列表查询用户集合
     *
     * @param userIds 用户ID列表
     * @return 结果
     */
    Map<Long, User> mapByUserIds(List<Long> userIds);

    /**
     * 修改用户信息
     *
     * @param dto 用户信息
     */
    void edit(UserEditDto dto);
}
