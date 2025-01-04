package org.example.springboot.system.common.aspectj;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.springboot.common.common.annotation.Dict;
import org.example.springboot.common.domain.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class DictAspectj {
    @Pointcut("execution(public * org.example.springboot.*.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethods() {
    }

    @Pointcut("execution(public * org.example.springboot.*.controller.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void excludeResponse() {
    }

    // 在方法成功执行后进行处理
    @AfterReturning(pointcut = "controllerMethods() && !excludeResponse()", returning = "result")
    public void doAfterReturning(Object result) {
        if (!(result instanceof Result<?> rs)) {
            return;
        }
        Object data = rs.getData();
        if (ObjectUtil.isEmpty(data)) {
            return;
        }
        switch (data) {
            case IPage<?> page -> {
                List<?> records = page.getRecords();
                records.forEach(this::processDictAnnotation);
            }
            case List<?> list -> list.forEach(this::processDictAnnotation);
            case Map map -> processDictAnnotation(data);
            case null, default -> processDictAnnotation(data);
        }
    }

    private void processDictAnnotation(Object result) {
        if (result == null) {
            return;
        }

        // 递归获取所有字段，包括父类的私有字段
        Field[] fields = getAllFields(result.getClass());
        for (Field field : fields) {
            Dict dictAnnotation = field.getAnnotation(Dict.class);
            if (dictAnnotation != null) {
                try {
                    // 反射获取字段值
                    field.setAccessible(true);
                    Object fieldValue = field.get(result);

                    if (fieldValue != null) {
                        // 通过反射获取枚举实例
                        Object enumInstance = ReflectionUtils.findMethod(dictAnnotation.enumClass(), dictAnnotation.fromMethod(), String.class)
                                .invoke(null, fieldValue.toString());

                        if (enumInstance != null) {
                            // 获取翻译后的值
                            Object translatedValue = ReflectionUtils.findMethod(enumInstance.getClass(), dictAnnotation.toMethod())
                                    .invoke(enumInstance);

                            // 在返回的结果中加入翻译后的字段
                            String translatedFieldName = field.getName() + "Text";
                            Field translatedField = null;
                            try {
                                translatedField = result.getClass().getDeclaredField(translatedFieldName);
                            } catch (NoSuchFieldException e) {
                                // 如果没有定义翻译字段，则通过反射设置一个新的字段
                                translatedField = ReflectionUtils.findField(result.getClass(), translatedFieldName);
                            }
                            if (translatedField != null) {
                                translatedField.setAccessible(true);
                                translatedField.set(result, translatedValue.toString());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // 这里可以添加日志或抛出自定义异常
                }
            }
        }
    }

    /**
     * 递归获取所有字段，包括父类的私有字段
     *
     * @param clazz 类对象
     * @return 所有字段数组
     */
    private Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }
}
