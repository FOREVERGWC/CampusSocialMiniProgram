package org.example.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.system.domain.dto.UserAuthDto;
import org.example.springboot.system.domain.entity.UserAuth;
import org.example.springboot.system.domain.vo.UserAuthVo;

import java.util.List;

/**
 * <p>
 * 用户三方授权服务类
 * </p>
 */
public interface IUserAuthService extends IService<UserAuth> {
    /**
     * 查询用户三方授权列表
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    List<UserAuthVo> getList(UserAuthDto dto);

    /**
     * 查询用户三方授权分页
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    IPage<UserAuthVo> getPage(UserAuthDto dto);

    /**
     * 查询用户三方授权
     *
     * @param dto 用户三方授权
     * @return 结果
     */
    UserAuthVo getOne(UserAuthDto dto);

    /**
     * 导出用户三方授权
     *
     * @param entity   用户三方授权
     * @param response 响应对象
     */
    void exportExcel(UserAuth entity, HttpServletResponse response);

    /**
     * 根据唯一标识查询用户三方授权
     *
     * @param openId 唯一标识
     * @return 结果
     */
    UserAuth getByOpenId(String openId);

    /**
     * 根据认证方式和唯一标识查询用户三方授权
     *
     * @param authType 认证方式
     * @param openId   唯一标识
     * @return 结果
     */
    UserAuth getByAuthTypeAndOpenIdAnd(Integer authType, String openId);
}
