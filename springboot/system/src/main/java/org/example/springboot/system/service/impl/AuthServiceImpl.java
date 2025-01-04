package org.example.springboot.system.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import org.example.springboot.common.common.enums.ResultCode;
import org.example.springboot.common.common.exception.ServiceException;
import org.example.springboot.common.utils.DataUtils;
import org.example.springboot.system.domain.entity.User;
import org.example.springboot.system.domain.model.LoginBody;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.domain.model.RegisterBody;
import org.example.springboot.system.domain.model.ResetBody;
import org.example.springboot.system.domain.vo.CaptchaVo;
import org.example.springboot.system.domain.vo.MenuVo;
import org.example.springboot.system.domain.vo.MetaVo;
import org.example.springboot.system.domain.vo.RouteVo;
import org.example.springboot.system.service.IAuthService;
import org.example.springboot.system.service.IMenuService;
import org.example.springboot.system.service.IUserRoleLinkService;
import org.example.springboot.system.service.IUserService;
import org.example.springboot.system.service.cache.ICaptchaService;
import org.example.springboot.system.strategy.LoginFactory;
import org.example.springboot.system.strategy.service.ILoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class AuthServiceImpl implements IAuthService {
    @Resource
    private LoginFactory loginFactory;
    @Resource
    private ICaptchaService captchaService;
    @Resource
    private IUserService userService;
    @Resource
    private IMenuService menuService;
    @Resource
    private IUserRoleLinkService userRoleLinkService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${captcha.enabled}")
    private Boolean captchaEnabled;

    @Override
    public CaptchaVo getCaptcha() {
        String uuid = UUID.fastUUID().toString();
        CaptchaVo vo = CaptchaVo.builder().uuid(uuid).enabled(captchaEnabled).build();
        if (!captchaEnabled) {
            return vo;
        }
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        String code = lineCaptcha.getCode();
        String img = lineCaptcha.getImageBase64Data();
        captchaService.setUuidLoginCode(uuid, code);
        vo.setImg(img);
        return vo;
    }

    @Override
    public String login(LoginBody body) {
        ILoginService loginService = loginFactory.getFactory(body.getLoginType());
        return loginService.login(body);
    }

    @Override
    public void logout(LoginUser user) {
        // TODO 把LogoutSuccessHandlerImpl逻辑移过来
    }

    @Transactional
    @Override
    public void register(RegisterBody body) {
        if (!Objects.equals(body.getPassword(), body.getConfirmPwd())) {
            throw new ServiceException(ResultCode.REGISTER_CONFIRM_ERROR);
        }
        User user = User.builder().build();
        BeanUtils.copyProperties(body, user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        captchaService.validateEmailRegisterCode(body.getEmail(), body.getCode());
        // TODO 2L替换为枚举
        userRoleLinkService.saveBatchByUserIdAndRoleIds(user.getId(), List.of(2L));
    }

    @Override
    public void resetPassword(ResetBody body) {
        if (!Objects.equals(body.getPassword(), body.getConfirmPwd())) {
            throw new ServiceException(ResultCode.RESET_CONFIRM_ERROR);
        }
        captchaService.validateEmailResetCode(body.getEmail(), body.getCode());
        User user = userService.getByEmail(body.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(body.getPassword()));
        userService.updateById(user);
    }

    @Override
    public List<RouteVo> getRoute() {
        List<MenuVo> menuList = menuService.getAuthList();
        List<RouteVo> routeList = menuList.stream().map(item -> RouteVo.builder()
                        .id(item.getId())
                        .parentId(item.getParentId())
                        .sort(item.getSort())
                        .path(item.getPath())
                        .name(item.getName())
                        .meta(MetaVo.builder()
                                .title(item.getTitle())
                                .icon(item.getIcon())
                                .hidden(!item.getVisible())
                                .build())
                        .component(item.getComponent())
                        .redirect(item.getRedirect())
                        .build())
                .toList();
        return DataUtils.listToTree(routeList,
                RouteVo::getParentId,
                RouteVo::setChildren,
                RouteVo::getId,
                0L,
                RouteVo::getSort,
                Comparator.naturalOrder());
    }
}
