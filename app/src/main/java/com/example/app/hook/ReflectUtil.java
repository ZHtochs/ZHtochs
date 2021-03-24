package com.example.app.hook;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static Object getField(Class clazz, Object target, String name) throws Exception {
        Field field = getField(clazz, name);
        return field.get(target);
    }

    public static Field getField(Class clazz, String name) throws Exception {
        Field field = findField(clazz, name);
        field.setAccessible(true);
        return field;
    }

    public static void setField(Class clazz, Object target, String name, Object value) throws Exception {
        Field field = getField(clazz, name);
        field.set(target, value);
    }

    private static Field findField(@NonNull Class clazz, @NonNull String name) throws NoSuchFieldException {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            for (Class<?> cls = clazz; cls != null; cls = cls.getSuperclass()) {
                try {
                    return cls.getDeclaredField(name);
                } catch (NoSuchFieldException ex) {
                    // Ignored
                }
            }
            throw e;
        }
    }
}
