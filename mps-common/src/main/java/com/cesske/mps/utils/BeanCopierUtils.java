package com.cesske.mps.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * BeanCopier的扩展
 * 构建的BeanCopier实例放进内存中管理提示性能节省开销
 */
public class BeanCopierUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanCopierUtils.class);

    private static final Map<String, BeanCopier> BEAN_COPIER_CONCURRENT_MAP = Maps.newConcurrentMap();

    private BeanCopierUtils() {
    }


    private static void copy(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier;
        if (!BEAN_COPIER_CONCURRENT_MAP.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, Boolean.FALSE);
            BEAN_COPIER_CONCURRENT_MAP.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CONCURRENT_MAP.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * 复制列表
     *
     * @param sourceList
     * @param sClass
     * @param <S>
     * @return
     */
    public static <S> List<S> invokeList(List<?> sourceList, Class<S> sClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        return Lists.transform(sourceList, input -> invoke(input, sClass));
    }


    /**
     * 把新对象不为空的属性，合并到旧对象中*
     *
     * @param target
     * @param source
     * @param <S>
     * @return
     */
    public static <S> S merge(S target, S source) {

        Class sourceBeanClass = source.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                Object obj = sourceField.get(source);
                if (obj instanceof String) {
                    if (!Strings.isNullOrEmpty(obj.toString()) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                        targetField.set(target, sourceField.get(source));
                    }
                } else if (obj instanceof Integer) {
                    if ((null != obj && ((Integer) obj).intValue() > 0) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                        targetField.set(target, sourceField.get(source));
                    }
                } else if (obj instanceof Long) {
                    if ((null != obj && ((Long) obj).intValue() > 0) && !"serialVersionUID".equals(sourceField.getName().toString())) {
                        targetField.set(target, sourceField.get(source));
                    }
                } else {
                    if (null != obj && !"serialVersionUID".equals(sourceField.getName().toString())) {
                        targetField.set(target, sourceField.get(source));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }


    /**
     * 复制对象
     *
     * @param source
     * @param sClass
     * @param <S>
     * @return
     */
    public static <S> S invoke(Object source, Class<S> sClass) {
        if (source == null) {
            return null;
        }
        S s = null;
        try {
            s = sClass.newInstance();
            copy(source, s);
        } catch (Exception e) {
            LOGGER.error(String.format("Create new instance of %s failed:", sClass), e);
        }
        return s;

    }
    public static <S> S invoke(S source) {
        if (source == null) {
            return null;
        }
        S s = null;
        try {
            s = (S)source.getClass().newInstance();
            copy(source, s);
        } catch (Exception e) {
            LOGGER.error(String.format("Create new instance of %s failed:", source.getClass()), e);
        }
        return s;
    }

}
