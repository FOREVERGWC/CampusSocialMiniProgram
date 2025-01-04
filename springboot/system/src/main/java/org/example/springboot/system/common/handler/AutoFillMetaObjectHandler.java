package org.example.springboot.system.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.example.springboot.system.domain.model.LoginUser;
import org.example.springboot.system.utils.UserUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser user = UserUtils.getLoginUser();
        String username = user == null ? "" : user.getUsername();
        Object create = metaObject.getValue("createBy");
        Object update = metaObject.getValue("updateBy");
        Object value = metaObject.getValue("remark");

        LocalDateTime date = LocalDateTime.now();
        Object createBy = create != null ? create : username;
        Object updateBy = update != null ? update : username;
        Object remark = value != null ? value : "";

        metaObject.setValue("createBy", createBy);
        metaObject.setValue("createTime", date);
        metaObject.setValue("updateBy", updateBy);
        metaObject.setValue("updateTime", date);
        metaObject.setValue("remark", remark);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser user = UserUtils.getLoginUser();
        String username = user == null ? "" : user.getUsername();
        Object update = metaObject.getValue("updateBy");

        LocalDateTime date = LocalDateTime.now();
        Object updateBy = update != null ? update : username;

        Object value = metaObject.getValue("remark");
        Object remark = value != null ? value : "";

        metaObject.setValue("updateBy", updateBy);
        metaObject.setValue("updateTime", date);
        metaObject.setValue("remark", remark);
    }
}
