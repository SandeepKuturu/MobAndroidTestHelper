package com.mob.android.test.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;

/**
 * This class will contain utilities for unit testing
 *
 * @author Sandeep Kuturu
*/
public final class TestUtils {
    private TestUtils() {
    }

    /**
     * This is the helper method to create a private object that has no arguments
     */
    public static <T> T invokePrivateConstructor(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return invokePrivateConstructor(clazz, new Class[]{});
    }

    /**
     * This is the helper method to create a private object
     */
    public static <T> T invokePrivateConstructor(Class<T> clazz, Class[] argtypes, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(argtypes);
        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }

    /**
     * This is the helper method to access and test private method that has no arguments
     */
    public static Object invokePrivateMethod(Object instance, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullPointerException {
        return invokePrivateMethod(instance, instance.getClass(), methodName, new Class[]{});
    }

    /**
     * This is the helper method to access and test private method
     */
    public static Object invokePrivateMethod(Object instance, String methodName, Class[] argtypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullPointerException {
        return invokePrivateMethod(instance, instance.getClass(), methodName, argtypes, args);
    }

    /**
     * This is the helper method to access and test private static method
     */
    public static Object invokePrivateStaticMethod(Class clazz, String methodName, Class[] argtypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullPointerException {
        return invokePrivateMethod(null, clazz, methodName, argtypes, args);
    }

    /**
     * This is the helper method to access and test private method (recursive)
     */
    private static Object invokePrivateMethod(Object instance, Class clazz, String methodName, Class[] argtypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NullPointerException {
        Method method;
        try {
            method = clazz.getDeclaredMethod(methodName, argtypes);
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() == null) {
                throw e;
            }
            return invokePrivateMethod(instance, clazz.getSuperclass(), methodName, argtypes, args);
        }

        method.setAccessible(true);
        return method.invoke(instance, args);
    }

    /**
     * Getter method to get access private variable
     */
    public static Object getVariable(Object instance, String variableName) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        return getVariable(instance, instance.getClass(), variableName);
    }

    /**
     * Getter method to get access private static variable
     */
    public static Object getStaticVariable(Class clazz, String variableName) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        return getVariable(null, clazz, variableName);
    }

    /**
     * Getter method to get access private variable (recursive)
     */
    private static Object getVariable(Object instance, Class clazz, String variableName) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        Field field;
        try {
            field = clazz.getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() == null) {
                throw e;
            }
            return getVariable(instance, clazz.getSuperclass(), variableName);
        }

        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Setter method to set private variable
     */
    public static void setVariable(Object instance, String variableName, Object value) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        setVariable(instance, instance.getClass(), variableName, value);
    }

    /**
     * Setter method to set private static variable
     */
    public static void setStaticVariable(Class clazz, String variableName, Object value) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        setVariable(null, clazz, variableName, value);
    }

    /**
     * Setter method to set private variable (recursive)
     */
    private static void setVariable(Object instance, Class clazz, String variableName, Object value) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        Field field;
        try {
            field = clazz.getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() == null) {
                throw e;
            }
            setVariable(instance, clazz.getSuperclass(), variableName, value);
            return;
        }

        field.setAccessible(true);
        field.set(instance, value);
    }

    /**
     * Used to cover methods that are generated for enums by the compiler
     */
    public static void verifyEnumStatics(Class enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        valuesMethod.setAccessible(true);
        Object[] values = (Object[]) valuesMethod.invoke(null);
        Method valueOfMethod = enumClass.getMethod("valueOf", String.class);
        valueOfMethod.setAccessible(true);
        assertEquals(values[0], valueOfMethod.invoke(null, ((Enum) values[0]).name()));
    }
}
