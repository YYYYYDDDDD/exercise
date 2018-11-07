package tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Description:bean操作工具类
 *
 * @author: Yujikang
 * Create at:  2018年10月29日 16时35分
 * Company: 沈阳艾尔时代科技发展有限公司
 * Copyright: (c)2018 AIR Times Inc. All rights reserved.
 * @version: 1.0
 */
public class BeanUtils {

    /**
     * 类拷贝
     *
     * @param sour 拷贝源
     * @param targ 拷贝体(目标)
     * @param types 泛型类型
     * @description 拷贝所有除数组外的Object包括集合、集合嵌套集合、类包含集合等；
     * 拷贝类的源Object可以直接调用，因为JVM在运行期间会抹掉局部变
     * 量的泛型信息，又叫泛型擦除，如果拷贝源是集合需要把拷贝体(目标)
     * 的泛型类型依次作为参数（Object.class）传给copyObject().
     * 理论可以无限嵌套，数组除外。
     * <p>
     * eg0:
     * ObjectBo bo = new ObjectBo();
     * ObjectVo vo = new ObjectVo();
     * BeanUtils.copyObject(bo,vo);
     * <p>
     * eg1:
     * List<ObjectBo> slist = ArrayList();
     * List<ObjectVo> tlist = ArrayList();
     * BeanUtils.copyObject(slist,tlist,ObjectVo.class);
     * <p>
     * eg2:如果赋值源与赋值体泛型类型一直，可以省略类型参数
     * ArrayList<HashSet<String>> slist = ArrayList();
     * ArrayList<HashSet<String>> tlist = ArrayList();
     * BeanUtils.copyObject(slist,tlist);
     * <p>
     * eg3:如果泛型不同，不用之前的参数一定要依次传入
     * HashMap<String,HashMap<ObjectBo,Integer>> smap =new HashMap<>();
     * HashMap<String,HashMap<ObjectVo,Integer>> tmap =new HashMap<>();
     * //如果全部传入
     * BeanUtils.copyObject(smap,tmap,String.class,HashMap.class,ObjectVo.class,Integer.class);
     * //当然，在ObjectVo.class之后没有变化，ObjectVo.class之后的，
     * //可以省略写成：
     * BeanUtils.copyObject(smap,tmap,String.class,HashMap.class,ObjectVo.class);
     */
    public static void copyObject(Object sour, Object targ, Class... types) {
        //判空
        if (sour == null || targ == null) {
            return;
        }
        //赋值源
        Class<?> sClass = sour.getClass();
        //赋值体
        Class<?> tClass = targ.getClass();
        //段是否是集合，集合不操作
        if (isCollection(sClass)) {
            if (isCollection(tClass)) {
                Iterator iterator = ((Collection) sour).iterator();
                try {

                    while (iterator.hasNext()) {

                        Object so = iterator.next();
                        Object to = null;

                        if (types != null && types.length > 0) {
                            to = getValue(types[0], so, subArray(types, 1, types.length - 1));
                        } else {
                            to = getValue(so.getClass(), so);
                        }
                        ((Collection) targ).add(to);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (isMap(sClass)) {
            try {
                if (isMap(tClass)) {
                    for (Map.Entry entry : (Set<Map.Entry>) ((Map) sour).entrySet()) {
                        Object tko = null;
                        Object tvo = null;
                        if (types != null && types.length > 0) {
                            tko = getValue(types[0], entry.getKey(), subArray(types, 1, types.length - 1));
                            if (types.length > 1) {
                                tvo = getValue(types[1], entry.getValue(), subArray(types, 2, types.length - 2));
                            } else {
                                tvo = getValue(entry.getValue().getClass(), entry.getValue());
                            }
                        } else {
                            tko = getValue(entry.getKey().getClass(), entry.getKey());
                            tvo = getValue(entry.getValue().getClass(), entry.getValue());
                        }
                        ((Map) targ).put(tko, tvo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        //赋值父类型
        try {
            assignSuperClass(sour, targ, sClass, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Field[] fields = sClass.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        //循环对字段赋值操作
        for (Field field : fields) {
            //获得源字段类型
            Class<?> fieldType = field.getType();

            try {
                //定义赋值体的字段类型
                Field tfield = getField(tClass, field.getName());
                //给字段赋值
                assignField(sour, targ, sClass, tClass, field, tfield);
            } catch (Exception e) {
                continue;
            }


        }

    }

    private static void assignField(Object sour, Object targ, Class sClass, Class tClass, Field sfield, Field tfield) throws NoSuchMethodException, IllegalAccessException, InstantiationException,
        InvocationTargetException {
        Method setter = tClass.getMethod("set" + initialUp(tfield.getName()), tfield.getType());
        Method getter = sClass.getMethod("get" + initialUp(sfield.getName()));
        //对比赋值源跟赋值体对应两个字段类型，如果两个类型不一致
        if (!sfield.getType().equals(tfield.getType())) {
            //如果类型不一样判断是不是对本类型对应的包装类型
            if (typeEquals(sfield.getType(), tfield.getType())) {
                setter.invoke(targ, getter.invoke(sour, null));
            } else {
                //如果类型不同，并且类型还都不是基本类型跟包装类
                if (!isPrimitive(tfield.getType()) && !isPackclass(tfield.getType()) && !isPrimitive(sfield.getType()) && !isPackclass(sfield.getType())) {
                    //是同类
                    Object to = tfield.getType().newInstance();
                    Object so = getter.invoke(sour, null);
                    //深复制
                    copyObject(so, to);
                    //赋值
                    setter.invoke(targ, to);
                }
            }
        } else {
            //判断是不是基本类型
            if (isPrimitive(sfield.getType()) || isPackclass(sfield.getType())) {
                Object value = (Object) getter.invoke(sour, null);
                if (value != null) {
                    //执行赋值
                    setter.invoke(targ, value);
                }
            } else {
                if (isCollection(sfield.getType())) {
                    //赋值体对应字段是null或者不是集合，退出
                    if (tfield == null || !isCollection(tfield.getType())) {
                        return;
                    }
                    //获取被赋值字段集合的泛型
                    Class tGenericity = (Class) ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[0];
                    //获取赋值源字段集合的值
                    Collection sColl = (Collection) getter.invoke(sour, null);
                    //如果集合为null，如果是一个null Collection，也赋值
                    if (sColl == null) {
                        return;
                    }
                    //通过赋值体对应字段创建一个集合
                    Collection tColl = (Collection) sColl.getClass().newInstance();
                    Iterator iterator = sColl.iterator();
                    if (iterator != null) {
                        while (iterator.hasNext()) {
                            Object to = getValue(tGenericity, iterator.next());
                            tColl.add(to);
                        }
                    }
                    setter.invoke(targ, tColl);

                } else if (isMap(sfield.getType())) {
                    //如果是map
                    //获取赋值源字段集合的值
                    Map cMap = (Map) getter.invoke(sour, null);
                    if (cMap == null) {
                        return;
                    }
                    //获取被赋值字段集合的泛型
                    //K
                    Class keyGenericity = (Class) ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[0];
                    //V
                    Class valueGenericity = (Class) ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[1];

                    //构建赋值体Map
                    Map tMap = (Map) cMap.getClass().newInstance();

                    //遍历Map
                    for (Map.Entry en : (Set<Map.Entry>) cMap.entrySet()) {

                        Object tko = getValue(keyGenericity, en.getKey());
                        Object tvo = getValue(valueGenericity, en.getValue());

                        tMap.put(tko, tvo);
                    }

                    setter.invoke(targ, tMap);
                } else {
                    //是同类
                    Object to = tfield.getType().newInstance();
                    Object so = getter.invoke(sour, null);
                    //深复制
                    copyObject(so, to);
                    //赋值
                    setter.invoke(targ, to);
                }
            }
        }
    }

    /**
     * 根据class获取对应的字段
     *
     * @param tClass
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    private static Field getField(Class<?> tClass, String fieldName) throws NoSuchFieldException {
        Field file = null;
        try {
            //根据名称去赋值类找
            file = tClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            //没找到 如果右面是 xxxBO换成xxxVO再找
            String stamp = fieldName.replace("BO", "VO");
            if (fieldName.equals(stamp)) {
                fieldName = fieldName.replace("VO", "BO");
            } else {
                fieldName = stamp;
            }
            try {
                file = tClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e1) {
                throw e1;
            }
        }
        return file;
    }

    private static final String OBJECT_STR = "java.lang.Object";

    /**
     * 为付类型赋值
     *
     * @param sour
     * @param targ
     * @param sClass
     * @param tClass
     */
    private static void assignSuperClass(Object sour, Object targ, Class sClass, Class tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //Source Super Class
        Class ssc = sClass.getSuperclass();
        //如果赋值源付类型是空或者是Object，返回
        if (ssc == null || OBJECT_STR.equals(ssc.getName())) {
            return;
        }

        //targer Super Class
        Class tsc = tClass.getSuperclass();
        //如果赋值体付类型是空或者是Object，返回
        if (tsc == null || OBJECT_STR.equals(tsc.getName())) {
            return;
        }

        Field[] sfields = ssc.getDeclaredFields();

        if (sfields == null && sfields.length == 0) {
            return;
        }
        for (Field field : sfields) {
            try {
                //获得对象字段反射
                Field tfield = getField(tsc, field.getName());
                //对字段赋值
                assignField(sour, targ, sClass, tClass, field, tfield);
            } catch (NoSuchFieldException e) {
                continue;
            }


        }

        //判断还有没有超类,如果有递归赋值
        assignSuperClass(sour, targ, ssc, tsc);

    }


    /**
     * 截取Class数组并返回
     *
     * @param classes
     * @param start
     * @param length
     * @return
     */
    public static Class[] subArray(Class[] classes, int start, int length) {
        Class[] claArr = null;
        if (classes.length >= start + length) {
            claArr = new Class[length];
            for (int i = 0; i < length; start++, i++) {
                claArr[i] = classes[start];
            }
        }
        return claArr;
    }

    /**
     * 创建对应类型，然后把对象付给这个类型并返回付完值的这个类型的对象
     *
     * @param aclass
     * @param value
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Object getValue(Class aclass, Object value, Class... types) throws IllegalAccessException, InstantiationException {
        if (value == null) {
            return null;
        }
        Object o = null;
        if (aclass != null) {
            if (!isPackclass(aclass)) {
                o = aclass.newInstance();
                copyObject(value, o, types);
            } else {
                o = value;
            }
        } else {
            if (!isPackclass(value.getClass())) {
                o = value.getClass().newInstance();
                copyObject(value, o, types);
            } else {
                o = value;
            }
        }
        return o;
    }

    /**
     * 判断是否是基本类型对象的包装类型或者包装类型对应的基本类型
     *
     * @param c1
     * @param c2
     * @return
     */
    private static boolean typeEquals(Class c1, Class c2) {
        boolean sign1 = isPrimitive(c1);
        boolean sign2 = isPackclass(c2);
        if (!sign1 ^ sign2) {
            if (sign1) {
                Class packclass = getPackclass(c1);
                return c2.equals(packclass);
            } else {
                Class packclass = getPackclass(c2);
                return c1.equals(packclass);
            }
        }
        return false;
    }

    /**
     * 获得基本类型对应的包装类型
     *
     * @param c
     * @return
     */
    private static Class getPackclass(Class c) {
        String typeName = c.getName();
        if (typeName.equals("int")) {
            return Integer.class;
        } else if (typeName.equals("double")) {
            return Double.class;
        } else if (typeName.equals("long")) {
            return Long.class;
        } else if (typeName.equals("byte")) {
            return Byte.class;
        } else if (typeName.equals("char")) {
            return Character.class;
        } else if (typeName.equals("boolean")) {
            return Boolean.class;
        } else if (typeName.equals("float")) {
            return Float.class;
        }
        return null;
    }

    /**
     * 判断是不是基本类型
     *
     * @param aclass
     * @return
     */
    private static boolean isPrimitive(Class aclass) {
        String[] types = {"int", "double", "long", "short", "byte", "boolean", "char", "float"};
        String typeName = aclass.getTypeName();
        for (String type : types) {
            if (typeName.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是不是包装类型
     *
     * @param aclass
     * @return
     */
    private static boolean isPackclass(Class aclass) {
        String[] types = {"java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String",};
        String typeName = aclass.getName();
        for (String type : types) {
            if (typeName.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 变大首字母
     *
     * @param str
     * @return
     */
    private static String initialUp(String str) {
        if (str == null || "".equals(str.trim())) {
            //后期做异常处理
            return null;
        }
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }

    /**
     * 根据class判断是否是一个集合（List and Set）
     *
     * @param aclass
     * @return
     */
    private static boolean isCollection(Class aclass) {

        String simpleName = aclass.getSimpleName();

        if ("List".equals(simpleName) || "Set".equals(simpleName)) {
            return true;
        }
        Class[] superclass = aclass.getInterfaces();
        for (Class c : superclass) {
            simpleName = c.getSimpleName();
            if ("List".equals(simpleName) || "Set".equals(simpleName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是一个Map
     *
     * @param aclass
     * @return
     */
    public static boolean isMap(Class aclass) {

        String simpleName = aclass.getSimpleName();

        if ("Map".equals(simpleName)) {
            return true;
        }
        Class[] superclass = aclass.getInterfaces();
        for (Class c : superclass) {
            simpleName = c.getSimpleName();
            if ("Map".equals(simpleName)) {
                return true;
            }
        }
        return false;
    }
}
