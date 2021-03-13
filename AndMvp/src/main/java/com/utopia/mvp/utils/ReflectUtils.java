package com.utopia.mvp.utils;

import java.lang.reflect.Type;


public class ReflectUtils {
    private final static String NAME_PREFIX = "class ";

    /**
     * 根据type获取class
     */
    public static Class<?> getClass(Type type) throws ClassNotFoundException {
        String className = getClassName(type);
        return Class.forName(className);
    }

    /**
     * 根据type获取className
     */
    private static String getClassName(Type type) {
        String fullName = type.toString();
        if (fullName.startsWith(NAME_PREFIX))
            return fullName.substring(NAME_PREFIX.length());
        return fullName;
    }
}
