package be.thebeehive.kata.util;

import java.lang.reflect.Field;

import lombok.SneakyThrows;

public class ReflectionUtil {
    @SneakyThrows
    public static Object getField(Object parent, String fieldName) {
        Field declaredField = parent.getClass()
                .getDeclaredField(fieldName);

        declaredField.setAccessible(true);
        return declaredField.get(parent);
    }
    @SneakyThrows
    public static void clearField(Object parent, String field) {
        Field declaredField = parent.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(parent, null);
    }

    @SneakyThrows
    public static void set(Object parent, String field, Object value) {
        Field declaredField = parent.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(parent, value);
    }
}
