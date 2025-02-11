package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.UserInfoDto;
import org.example.springboot.system.domain.entity.UserInfo;
import org.example.springboot.system.domain.vo.UserInfoVo;

import java.util.List;

/**
 * <p>
 * 用户信息服务类
 * </p>
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 查询用户信息列表
     *
     * @param dto 用户信息
     * @return 结果
     */
    List<UserInfoVo> getList(UserInfoDto dto);

    /**
     * 查询用户信息分页
     *
     * @param dto 用户信息
     * @return 结果
     */
    IPage<UserInfoVo> getPage(UserInfoDto dto);

    /**
     * 查询用户信息
     *
     * @param dto 用户信息
     * @return 结果
     */
    UserInfoVo getOne(UserInfoDto dto);

    /**
     * 查询我的用户信息
     *
     * @return 结果
     */
    UserInfoVo getMyOne();

    /**
     * 导出用户信息
     *
     * @param entity   用户信息
     * @param response 响应对象
     */
    void exportExcel(UserInfo entity, HttpServletResponse response);
}
