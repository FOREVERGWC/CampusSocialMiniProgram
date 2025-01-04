package org.example.springboot.system.strategy;

import org.example.springboot.system.common.enums.LoginType;
import org.example.springboot.system.strategy.service.ILoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LoginFactory {
    private final ILoginService usernamePasswordLoginServiceImpl;

    private final ILoginService emailCodeLoginServiceImpl;

    private final ILoginService phoneCodeLoginServiceImpl;

    public LoginFactory(@Qualifier("usernamePasswordLoginServiceImpl") ILoginService usernamePasswordLoginServiceImpl,
                        @Qualifier("emailCodeLoginServiceImpl") ILoginService emailCodeLoginServiceImpl,
                        @Qualifier("phoneCodeLoginServiceImpl") ILoginService phoneCodeLoginServiceImpl) {
        this.usernamePasswordLoginServiceImpl = usernamePasswordLoginServiceImpl;
        this.emailCodeLoginServiceImpl = emailCodeLoginServiceImpl;
        this.phoneCodeLoginServiceImpl = phoneCodeLoginServiceImpl;
    }

    /**
     * 登录策略
     *
     * @param loginType 登录类型
     * @return 结果
     */
    public ILoginService getFactory(LoginType loginType) {
        ILoginService strategy;
        switch (loginType) {
            case LoginType.EMAIL_CODE -> strategy = emailCodeLoginServiceImpl;
            case LoginType.PHONE_CODE -> strategy = phoneCodeLoginServiceImpl;
            default -> strategy = usernamePasswordLoginServiceImpl;
        }
        return strategy;
    }
}
