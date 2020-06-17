package com.github.tanxinzheng.framework.mybatis.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * @Description 复制对象工具类
 * @Author tanxinzheng
 * @Date 2020/6/16
 */
public class BeanCopierUtils {

    /**
     * 对象拷贝
     * @param source        源对象
     * @param clazz         目标对象类型class
     * @param <T>           目标对象类型
     * @return              目标对象
     */
    public static <T> T copy(Object source, Class<T> clazz){
        if(source == null){
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), clazz, false);
        return copy(source, clazz, beanCopier);
    }

    /**
     * 对象拷贝
     * @param source        源对象
     * @param clazz         目标对象类型class
     * @param beanCopier    工具类
     * @param <T>           目标对象类型
     * @return              目标对象
     */
    public static <T> T copy(Object source, Class<T> clazz, BeanCopier beanCopier){
        if(source == null){
            return null;
        }
        T target = null;
        try {
            target = clazz.newInstance();
            beanCopier.copy(source, target, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 列表对象拷贝
     * @param sources 源列表
     * @param clazz 源列表对象Class
     * @param <T> 目标列表对象类型
     * @param <M> 源列表对象类型
     * @return 目标列表
     */
    public static <T, M> List<T> copy(List<M> sources, Class<T> clazz) {
        if(Objects.isNull(sources)){
            return null;
        }
        if(sources.isEmpty()){
            return Lists.newArrayList();
        }
        if (Objects.isNull(clazz)){
            throw new IllegalArgumentException("clazz parameter can't be not null");
        }
        BeanCopier copier = BeanCopier.create(sources.get(0).getClass(), clazz, false);
        return Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream().map(m -> copy(m, clazz, copier))
                .collect(Collectors.toList());
    }

    /**
     * 分页对象拷贝
     * @param sources 源列表
     * @param clazz 源列表对象Class
     * @param <T> 目标列表对象类型
     * @param <M> 源列表对象类型
     * @return 目标列表
     */
    public static <T, M> IPage<T> copy(IPage<M> sources, Class<T> clazz) {
        if(Objects.isNull(sources)){
            return null;
        }
        if (Objects.isNull(clazz))
            throw new IllegalArgumentException();
        BeanCopier copier = BeanCopier.create(sources.getRecords().get(0).getClass(), clazz, false);
        Page<T> page = new Page<T>(sources.getCurrent(), sources.getSize(), sources.getTotal(), sources.isSearchCount());
        List<T> data = Optional.of(sources.getRecords())
                .orElse(new ArrayList<>())
                .stream().map(m -> copy(m, clazz, copier))
                .collect(Collectors.toList());
        page.setRecords(data);
        return page;
    }
}
