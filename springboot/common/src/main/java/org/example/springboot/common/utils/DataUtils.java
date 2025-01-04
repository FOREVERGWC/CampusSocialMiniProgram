package org.example.springboot.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataUtils {
    /**
     * 列表对象转树形对象
     *
     * @param list           列表对象
     * @param parentIdField  父级ID字段
     * @param childrenField  子级字段
     * @param idField        ID字段
     * @param parentId       父级ID
     * @param sortField      排序字段
     * @param sortComparator 入参泛型
     * @param <T>            父级ID类型
     * @param <U>            父级ID类型
     * @param <V>            排序字段类型
     * @return 子级列表
     */
    public static <T, U, V> List<T> listToTree(List<T> list, Function<T, U> parentIdField, Setter<T, List<T>> childrenField, Function<T, U> idField, U parentId, Function<T, V> sortField, Comparator<V> sortComparator) {
        if (CollectionUtil.isEmpty(list)) {
            return List.of();
        }
        if (sortField != null && sortComparator != null) {
            list = list.stream().sorted(Comparator.comparing(sortField, sortComparator)).collect(Collectors.toList());
        }
        Map<U, List<T>> groupedByParentId = list.stream().collect(Collectors.groupingBy(parentIdField, LinkedHashMap::new, Collectors.toList()));
        return buildTree(groupedByParentId, parentId, childrenField, idField);
    }

    /**
     * 递归构建树形结构
     *
     * @param map           分组数据
     * @param parentId      父级ID
     * @param childrenField 子级字段
     * @param idField       ID字段
     * @param <T>           入参泛型
     * @param <U>           父级ID类型
     * @return 子级列表
     */
    private static <T, U> List<T> buildTree(Map<U, List<T>> map, U parentId, Setter<T, List<T>> childrenField, Function<T, U> idField) {
        List<T> children = map.getOrDefault(parentId, new ArrayList<>());
        for (T child : children) {
            childrenField.accept(child, buildTree(map, idField.apply(child), childrenField, idField));
        }
        return children;
    }

    @FunctionalInterface
    public interface Setter<T, P> {
        void accept(T t, P p);
    }

    /**
     * 递归获取祖级ID
     *
     * @param parentId 父级ID
     * @param getById  获取对象的方法引用
     * @param <T>      实体类型
     * @return 祖级ID
     */
    public static <T> Long getAncestorId(Long parentId, Function<Long, T> getById, Function<T, Long> getParentIdFunction) {
        T entity = getById.apply(parentId);
        if (entity == null || getParentIdFunction.apply(entity) == 0L) {
            return entity == null ? 0L : parentId;
        }
        return getAncestorId(getParentIdFunction.apply(entity), getById, getParentIdFunction);
    }

    /**
     * 实体对象转换为VO对象
     *
     * @param item    实体对象
     * @param voClass VO类类型
     * @param <T>     实体类泛型
     * @return VO对象
     */
    public static <T> T convertToVo(T item, Class<? extends T> voClass) {
        try {
            // TODO 考虑使用MapStruct代替BeanUtils.copyProperties
            Constructor<? extends T> constructor = voClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T vo = constructor.newInstance();
            BeanUtils.copyProperties(item, vo);
            return vo;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
