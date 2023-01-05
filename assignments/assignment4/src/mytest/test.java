package mytest;

import dependency_injection.Inject;
import dependency_injection.Value;
import testclass.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class test {
    private static Properties injectProp;
    private static Properties valueProp;

    public static void loadInjectProperties(File file) {
        injectProp = new Properties();
        try {
            InputStream in = new BufferedInputStream(Files.newInputStream(file.toPath()));
            injectProp.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadValueProperties(File file) {
        valueProp = new Properties();
        try {
            InputStream in = new BufferedInputStream(Files.newInputStream(file.toPath()));
            valueProp.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        loadInjectProperties(new File("local-inject.properties"));
        loadValueProperties(new File("local-value.properties"));

        Test instance = createInstance(Test.class);
        System.out.println(instance.getVal());
        System.out.println(instance.getVal().length);
//        assertEquals("", instance.getVal());

    }


    public static <T> T createInstance(Class<T> clazz) {
        Constructor<?> constructor = null;
        Object object;
        try {
            // 第一步：找实现类
            String implClazzName = clazz.getName();
            if (injectProp.containsKey(clazz.getName())) {
                implClazzName = injectProp.getProperty(clazz.getName());
            }

            // 第二步：确定构造方法
            Class<?> implClazz = Class.forName(implClazzName);
            for (Constructor<?> c : implClazz.getDeclaredConstructors()) {
                if (c.getAnnotation(Inject.class) != null) {
                    constructor = c;
                }
            }
            // 第三步：构造对象
            if (constructor == null) {
                constructor = implClazz.getDeclaredConstructor();
                object = constructor.newInstance();
            } else {
                // 1、找构造方法中的参数，并对其赋值，然后存进一个object数组中，用以后面构造对象
                int count = constructor.getParameterCount();
                Parameter[] temp = constructor.getParameters();
                Object[] params = new Object[count];
                for (int i = 0; i < count; i++) {
                    Parameter p = temp[i];
                    if (p.getAnnotation(Value.class) != null) {
                        Value valueAnnotation = p.getAnnotation(Value.class);
                        if (p.getType().isPrimitive()) {
                            params[i] = dealWithPrimitive(p.getType(), valueAnnotation);
                        } else {
                            params[i] = dealWithNonPrimitive(p, null, valueAnnotation);
                        }
                    } else {
                        params[i] = createInstance(p.getType());
                    }
                }
                object = constructor.newInstance(params);
            }

            // 第四步：在已有对象中注入属性
            Field[] fields = implClazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Value.class) != null) {
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    if (field.getType().isPrimitive()) {
                        if (isPublic(field)) {
                            field.set(object, dealWithPrimitive(field.getType(), valueAnnotation));
                        } else {
                            field.setAccessible(true);
                            field.set(object, dealWithPrimitive(field.getType(), valueAnnotation));
                            field.setAccessible(false);
                        }
                    } else {
                        if (isPublic(field)) {
                            field.set(object, dealWithNonPrimitive(null, field, valueAnnotation));
                        } else {
                            field.setAccessible(true);
                            field.set(object, dealWithNonPrimitive(null, field, valueAnnotation));
                            field.setAccessible(false);
                        }
                    }
                } else if (field.getAnnotation(Inject.class) != null) {
                    if (isPublic(field)) {
                        field.set(object, createInstance(field.getType()));
                    } else {
                        field.setAccessible(true);
                        field.set(object, createInstance(field.getType()));
                        field.setAccessible(false);
                    }
                }
            }

        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return (T) object;
    }

    //高级写法：
    //            constructor = Arrays.stream(clazz.getDeclaredConstructors()).filter(
    //                    c -> c.getAnnotation(Inject.class) != null
    //            ).findFirst().orElse(clazz.getDeclaredConstructor());

    public static Object dealWithPrimitive(Class<?> clazz, Value valueAnnotation) {
        String value = "";
        if (valueAnnotation != null) {
            value = valueAnnotation.value();
            if (valueProp.containsKey(value)) {
                value = valueProp.getProperty(value);
            }
        }
        if (clazz == int.class) {
            if (valueAnnotation == null) {
                return 0;
            } else {
                int int_value = 0;
                String[] values = value.split(valueAnnotation.delimiter());
                for (String s : values) {
                    if (stringToInt(s)) {
                        int_value = Integer.parseInt(s);
                        return int_value;
                    }
                }
                return int_value;
            }
        } else if (clazz == boolean.class) {
            if (valueAnnotation == null) {
                return false;
            } else {
                boolean flag = false;
                String[] values = value.split(valueAnnotation.delimiter());
                for (String s : values) {
                    if (canStringToBool(s))
                        if (stringToBool(s)) {
                            flag = true;
                            break;
                        }
                }
                return flag;
            }
        } else {
            return 0;
        }
    }

    public static boolean stringToInt(String s) {
        if (s.equals("")) {
            return false;
        } else {
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            long l = Long.parseLong(s);
            return l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE;
        }
    }

    public static boolean canStringToBool(String s) {
        s = s.toLowerCase();
        return s.equals("true") || s.equals("false");
    }

    public static boolean stringToBool(String s){
        s = s.toLowerCase();
        if (s.equals("true"))
            return true;
        else
            return false;
    }

    public static boolean isPublic(Field field) {
        int m = field.getModifiers();
        return Modifier.isPublic(m);
    }

    public static Object dealWithNonPrimitive(Parameter p, Field field, Value valueAnnotation) {
        Class<?> clazz;
        if (p != null)
            clazz = p.getType();
        else
            clazz = field.getType();
        String value = "";
        if (valueAnnotation != null) {
            value = valueAnnotation.value();
            if (valueProp.containsKey(value)) {
                value = valueProp.getProperty(value);
            }
        }
        try {
            if (clazz == String.class) {
                if (valueAnnotation == null) {
                    return "";
                }
                String[] values = value.split(valueAnnotation.delimiter());
                return values[0];
            } else if (clazz == boolean[].class) {
                if (valueAnnotation == null) {
                    return new boolean[0];
                }
                value = value.substring(1, value.length() - 1);
                String[] values = value.split(valueAnnotation.delimiter());
                ArrayList<Boolean> temp = new ArrayList<>();
                for (String s : values) {
                    if (canStringToBool(s))
                        temp.add(stringToBool(s));
                }
                boolean[] booleans = new boolean[temp.size()];
                for (int i = 0; i < booleans.length; i++) {
                    booleans[i] = temp.get(i);
                }
                return booleans;
            } else if (clazz == int[].class) {
                if (valueAnnotation == null) {
                    return new int[0];
                }
                value = value.substring(1, value.length() - 1);
                String[] values = value.split(valueAnnotation.delimiter());
                ArrayList<Integer> temp = new ArrayList<>();
                for (String s : values) {
                    if (stringToInt(s)) {
                        temp.add(Integer.parseInt(s));
                    }
                }
                int[] ints = new int[temp.size()];
                for (int i = 0; i < temp.size(); i++) {
                    ints[i] = temp.get(i);
                }
                return ints;
            } else if (clazz == String[].class) {
                if (valueAnnotation == null) {
                    return new String[0];
                }
                value = value.substring(1, value.length() - 1);
                if (value.equals(""))
                    return new String[0];
                return value.split(valueAnnotation.delimiter());
            } else if (clazz == List.class) {
                ParameterizedType type;
                if (p != null)
                    type = (ParameterizedType) p.getParameterizedType();
                else
                    type = (ParameterizedType) field.getGenericType();
                Class<?> itemType = Class.forName(type.getActualTypeArguments()[0].getTypeName());
                value = value.substring(1, value.length() - 1);
                String[] items = value.split(valueAnnotation.delimiter());
                List<Object> temp = new ArrayList<>();
                if (itemType == Boolean.class) {
                    for (String s : items) {
                        s = s.toLowerCase();
                        if (s.equals("true") || s.equals("false")) {
                            temp.add(s.equals("true"));
                        }
                    }
                } else if (itemType == Integer.class) {
                    for (String s : items) {
                        if (stringToInt(s)) {
                            temp.add(Integer.parseInt(s));
                        }
                    }
                } else if (itemType == String.class) {
                    if (value.equals(""))
                        temp = Arrays.asList(new String[0]);
                    else
                        temp = Arrays.asList(items);
                }
                return temp;
            } else if (clazz == Set.class) {
                ParameterizedType type;
                if (p != null)
                    type = (ParameterizedType) p.getParameterizedType();
                else
                    type = (ParameterizedType) field.getGenericType();
                Class<?> itemType = Class.forName(type.getActualTypeArguments()[0].getTypeName());
                value = value.substring(1, value.length() - 1);
                String[] items = value.split(valueAnnotation.delimiter());
                Set<Object> temp = new HashSet<>();
                if (itemType == Boolean.class) {
                    for (String s : items) {
                        s = s.toLowerCase();
                        if (s.equals("true") || s.equals("false")) {
                            temp.add(s.equals("true"));
                        }
                    }
                } else if (itemType == Integer.class) {
                    for (String s : items) {
                        if (stringToInt(s)) {
                            temp.add(Integer.parseInt(s));
                        }
                    }
                } else if (itemType == String.class) {
                    if (value.equals(""))
                        temp = new HashSet<>(0);
                    else
                        temp = new HashSet<>(List.of(temp));
                }
                return temp;
            } else if (clazz == Map.class) {
                ParameterizedType type;
                if (p != null)
                    type = (ParameterizedType) p.getParameterizedType();
                else
                    type = (ParameterizedType) field.getGenericType();
                Class<?> keyType = Class.forName(type.getActualTypeArguments()[0].getTypeName());
                Class<?> valueType = Class.forName(type.getActualTypeArguments()[1].getTypeName());
                value = value.substring(1, value.length() - 1);
                String[] items = value.split(valueAnnotation.delimiter());
                Map<Object, Object> map = new HashMap<>();
                for (String s : items) {
                    String itemKey = s.split(":")[0];
                    String itemValue = s.split(":")[1];
                    if (keyType == Boolean.class && valueType == Boolean.class) {
                        if (canStringToBool(itemKey) && canStringToBool(itemValue)) {
                            map.put(stringToBool(itemKey), stringToBool(itemValue));
                        }
                    } else if (keyType == Boolean.class && valueType == Integer.class) {
                        if (canStringToBool(itemKey) && stringToInt(itemValue)) {
                            map.put(stringToBool(itemKey), Integer.parseInt(itemValue));
                        }
                    } else if (keyType == Boolean.class && valueType == String.class) {
                        if (canStringToBool(itemKey)) {
                            map.put(stringToBool(itemKey), itemValue);
                        }
                    } else if (keyType == Integer.class && valueType == Boolean.class) {
                        if (stringToInt(itemKey) && canStringToBool(itemValue)) {
                            map.put(Integer.parseInt(itemKey), stringToBool(itemValue));
                        }
                    } else if (keyType == Integer.class && valueType == Integer.class) {
                        if (stringToInt(itemKey) && stringToInt(itemValue)) {
                            map.put(Integer.parseInt(itemKey), Integer.parseInt(itemValue));
                        }
                    } else if (keyType == Integer.class && valueType == String.class) {
                        if (stringToInt(itemKey)) {
                            map.put(Integer.parseInt(itemKey), itemValue);
                        }
                    } else if (keyType == String.class && valueType == Boolean.class) {
                        if (canStringToBool(itemValue)) {
                            map.put(itemKey, stringToBool(itemValue));
                        }
                    } else if (keyType == String.class && valueType == Integer.class) {
                        if (stringToInt(itemValue)) {
                            map.put(itemKey, Integer.parseInt(itemValue));
                        }
                    } else if (keyType == String.class && valueType == String.class) {
                        map.put(itemKey, itemValue);
                    }
                }
                return map;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
