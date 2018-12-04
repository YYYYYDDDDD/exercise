package tools;

import java.lang.reflect.*;
import java.util.*;


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
     * @param sour  复制源（被copy对象）
     * @param targ  复制体（copy对象）
     * @param types 泛型类型
     * @description 拷贝所有Object对象。包括集合、集合嵌套集合、类包含集合、数组等； 该工具对对象的拷贝为深拷贝。
     *              复制源需要提供被复制字段的getter方法，复制体也要提供对应复制字段的setter方法。
     *              该工具类根据“字段的字段名”进行匹配复制，比如复制源类中Long型的age字段会对应到复制体的age字段，
     *              这样才能进行复制操作。
     *              如果字段名中存在BO或者VO字符串，将进行两次次匹配的模式。比如复制源的字段类型是ArrayList<ObjectBO>
     *              类型，字段名为objListBO，首先会查找在复制体中字段名为objListBO的字段，如果不存在会再一次去查找
     *              字段名为objListVO的字段，如果还不存在将放弃复制，继续操作其余的字段。 所以理论上字段名objListBO
     *              跟字段名objListVO都看做为一个字段处理，请在字段命名的时候注意。
     *              基本类型与包装类之间对应会被成功复制。如复制源Long型的age，复制体的age字段为long型会被成功复制。
     *              但如果基本类型跟包装类型之间不对应，会复制失败，如Long型的age复制体为int型失败。
     *              <p>
     *              注意： 如果拷贝源是集合或者Map，需要把拷贝体的泛型类型对应的calss对象作为参数（Object.class）
     *              依次追加到copyObject()方法后面.但如果集合在是作为被拷贝类的属性字段进行复制，则不需要这样做。
     *              只有在集合与集合、Map与Map之间的直接拷贝才需要这样做。
     *              <p>
     *              理论可以无限嵌套。
     *              <p>
     *              1.bean之间copy <br>
     *              eg0: <br>
     *              ObjectBo bo = new ObjectBo(); <br>
     *              ObjectVo vo = new ObjectVo(); <br>
     *              BeanUtils.copyObject(bo,vo);
     *              <p>
     *              2.集合、Map之间的copy <br>
     *              eg1: <br>
     *              List<List<ObjectBo>> slist = new ArrayList();
     *              List<List<ObjectVo>> tlist = new ArrayList();
     *              //一定要传你要复制的list类型的实现类,这里我要把ArrayList当作tlist的元素，并且这个ArrayList的泛型
     *              //是ObjectVo，对应的Class对象就是ArrayList.class,ObjectVo.class,当作参数依次传入
     *              BeanUtils.copyObject(slist,tlist,ArrayList.class,ObjectVo.class);
     *              <p>
     *              eg2:如果复制源与复制体泛型类型一直，可以省略类型参数 <br>
     *              ArrayList<HashSet<ObjectBo>> slist = new ArrayList();
     *              ArrayList<HashSet<ObjectBo>> tlist = new ArrayList();
     *              BeanUtils.copyObject(slist,tlist);
     *              <p>
     *              eg3:如果泛型不同，不同泛型之前的参数一定要依次传入<br>
     *              HashMap<String,HashMap<ObjectBo,Integer>> smap =new HashMap<>();
     *              HashMap<String,HashMap<ObjectVo,Integer>> tmap =new HashMap<>();
     *              //泛型类型依找顺序依次传入<br>
     *              BeanUtils.copyObject(smap,tmap,String.class,HashMap.class,ObjectVo.class,Integer.class);
     *              //当然，在ObjectVo.class之后没有变化，ObjectVo.class之后的可以省略，写成:<br>
     *              BeanUtils.copyObject(smap,tmap,String.class,HashMap.class,ObjectVo.class);
     *              <p>
     *              3.数组之间的copy <br>
     *              eg4: 数组的复制注意一点即可，就是复制体一定要实例出大小，否则无法进行复制,比如 <br>
     *              ObjectBo[][] sArr = new ObjectBo[a][b]; <br>
     *              ObjectVo[][] tArr = new ObjectVo[>=a][>=b];
     *              //初始化的时候要指定二维数组的大小，创建一个一维数组的长度大于等于a,二维数组的长度大于等于b的数组才会被正确复制 <br>
     *              BeanUtils.copyObject(sArr,tArr);
     * @version 1.0.2
     *
     *
     */

    public static void copyObject(Object sour, Object targ, Class<?>... types) {
        // 判空
        if (sour == null || targ == null) {
            return;
        }
        // 获得复制源反射对象
        Class<?> sClass = sour.getClass();
        // 获得复制体反射对象
        Class<?> tClass = targ.getClass();

        // 获得类型
        switch (type(sClass)) {

            case COLLECTION_TYPE: {
                // 处理集合复制
                assignCollection(sour, targ, types);
                break;
            }
            case MAP_TYPE: {
                // 处理map复制
                assignMap(sour, targ, types);
                break;
            }
            case ARRAY_TYPE: {
                // 处理数组复制
                assignArray(getDimensionType(tClass), sour, targ, types);
                break;
            }
            case DATE_TYPE:{
                if(type(tClass) == DATE_TYPE){
                    ((Date)targ).setTime(((Date)sour).getTime());
                }
                break;
            }
            default: {
                // 其他对象
                // 递归复制超类属性
                assignSuperClass(sour, targ, sClass, tClass);
                // 获取字段
                Field[] fields = sClass.getDeclaredFields();
                if (fields != null && fields.length != 0) {
                    // 循环对字段复制操作
                    for (Field field : fields) {
                        try {
                            // 获得定义复制体的字段类型
                            Field tfield = getField(tClass, field.getName());
                            // 给该字段复制
                            assignField(sour, targ, sClass, tClass, field, tfield);
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }

            }
        }

    }

    /**
     * map复制
     *
     * @param sour
     * @param targ
     * @param types
     */
    private static void assignMap(Object sour, Object targ, Class<?>[] types) {

        try {
            if (type(targ.getClass()) == MAP_TYPE) {
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) sour).entrySet()) {
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
                    ((Map<Object, Object>) targ).put(tko, tvo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 集合复制
     *
     * @param sour
     * @param targ
     * @param types
     */
    private static void assignCollection(Object sour, Object targ, Class<?>... types) {
        if (type(targ.getClass()) == COLLECTION_TYPE) {
            Iterator<?> iterator = ((Collection<?>) sour).iterator();
            try {

                while (iterator.hasNext()) {

                    Object so = iterator.next();
                    Object to = null;

                    if (types != null && types.length > 0) {
                        to = getValue(types[0], so, subArray(types, 1, types.length - 1));
                    } else {
                        to = getValue(so.getClass(), so);
                    }
                    ((Collection<Object>) targ).add(to);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字段复制
     *
     * @param sour
     * @param targ
     * @param sClass
     * @param tClass
     * @param sfield
     * @param tfield
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    private static void assignField(Object sour, Object targ, Class<?> sClass, Class<?> tClass, Field sfield,
                                    Field tfield)
        throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method setter = tClass.getMethod("set" + initialUp(tfield.getName()), tfield.getType());
        Method getter = sClass.getMethod("get" + initialUp(sfield.getName()));
        // 对比复制源跟复制体对应两个字段类型，如果两个类型不一致
        if (!sfield.getType().equals(tfield.getType())) {
            // 如果类型不一样判断是不是对本类型对应的包装类型
            if (typeEquals(sfield.getType(), tfield.getType())) {
                setter.invoke(targ, getter.invoke(sour));
            } else {
                // 如果类型不同，并且类型还都不是基本类型跟包装类
                if (!isPrimitive(tfield.getType()) && !isPackclass(tfield.getType()) && !isPrimitive(sfield.getType())
                    && !isPackclass(sfield.getType())) {
                    // 是同类
                    Object to = tfield.getType().newInstance();
                    Object so = getter.invoke(sour);
                    // 递归，进行深复制
                    copyObject(so, to);
                    // 复制
                    setter.invoke(targ, to);
                }
            }
        } else if (isPrimitive(sfield.getType()) || isPackclass(sfield.getType())) {
            // 判断是不是基本类型
            Object value = (Object) getter.invoke(sour);
            if (value != null) {
                // 执行复制
                setter.invoke(targ, value);
            }
        } else if (type(sfield.getType()) == COLLECTION_TYPE) {
            // 复制体对应字段是null或者不是集合，退出
            if (tfield == null || type(tfield.getType()) != COLLECTION_TYPE) {
                return;
            }
            // 获取被复制字段集合的泛型
            Type tGenericity = ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[0];
            // 获取复制源字段集合的值
            Collection<?> sColl = (Collection<?>) getter.invoke(sour);
            // 如果集合为null，如果是一个null Collection，也复制
            if (sColl == null) {
                return;
            }
            // 通过复制体对应字段创建一个集合
            Collection<Object> tColl = sColl.getClass().newInstance();
            Iterator<?> iterator = sColl.iterator();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    Object to = getValue(tGenericity, iterator.next());
                    tColl.add(to);
                }
            }
            setter.invoke(targ, tColl);

        } else if (type(sfield.getType()) == MAP_TYPE) {
            if (tfield == null || type(tfield.getType()) != MAP_TYPE) {
                return;
            }
            // 如果是map
            // 获取复制源字段集合的值
            Map<?, ?> cMap = (Map<?, ?>) getter.invoke(sour);
            if (cMap == null) {
                return;
            }
            // 获取被复制字段集合的泛型
            // K
            Type keyType = ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[0];
            // V
            Type valueType = ((ParameterizedType) tfield.getGenericType()).getActualTypeArguments()[1];

            // 构建复制体Map
            Map<Object, Object> tMap = cMap.getClass().newInstance();

            // 遍历Map
            for (Map.Entry<?, ?> en : cMap.entrySet()) {

                Object tko = getValue(keyType, en.getKey());
                Object tvo = getValue(valueType, en.getValue());
                tMap.put(tko, tvo);
            }

            setter.invoke(targ, tMap);
        } else {
            // 是同类
            Object to = tfield.getType().newInstance();
            Object so = getter.invoke(sour);
            // 递归，继续深复制
            copyObject(so, to);
            // 得到复制结果进行复制
            setter.invoke(targ, to);
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
            // 根据名称去复制类找
            file = tClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 没找到 如果右面是 xxxBO换成xxxVO再找
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
     * 为付类型复制
     *
     * @param sour
     * @param targ
     * @param sClass
     * @param tClass
     */
    private static void assignSuperClass(Object sour, Object targ, Class<?> sClass, Class<?> tClass) {
        // Source Super Class
        Class<?> ssc = sClass.getSuperclass();
        // 如果复制源付类型是空或者是Object，返回
        if (ssc == null || OBJECT_STR.equals(ssc.getName())) {
            return;
        }

        // targer Super Class
        Class<?> tsc = tClass.getSuperclass();
        // 如果复制体付类型是空或者是Object，返回
        if (tsc == null || OBJECT_STR.equals(tsc.getName())) {
            return;
        }

        Field[] sfields = ssc.getDeclaredFields();

        if (sfields == null || sfields.length == 0) {
            return;
        }
        for (Field field : sfields) {
            try {
                // 获得对象字段反射
                Field tfield = getField(tsc, field.getName());
                // 对字段复制
                assignField(sour, targ, sClass, tClass, field, tfield);
            } catch (Exception e) {
                continue;
            }

        }

        // 判断还有没有超类,如果有递归复制
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
    private static Class<?>[] subArray(Class<?>[] classes, int start, int length) {
        Class<?>[] claArr = null;
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
    private static Object getValue(Class<?> aclass, Object value, Class<?>... types)
        throws IllegalAccessException, InstantiationException {
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

    private static Object getValue(Type type, Object value, Class<?>... types)
        throws IllegalAccessException, InstantiationException {
        if (value == null) {
            return null;
        }
        if (type(type) == OTHER_TYPE) {
            return getValue((Class<?>) type, value, types);
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        Object o = value.getClass().newInstance();
        // 存储对象类型
        List<Class<?>> typeList = new LinkedList<>();
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            for (Type t : actualTypeArguments) {
                if (t != null) {
                    if (t instanceof ParameterizedType) {
                        LinkedList<Class<?>> genericityTypes = getGenericityTypes(type, value);
                        genericityTypes.removeFirst();
                        typeList.addAll(genericityTypes);
                    } else {
                        typeList.add((Class<?>) t);
                    }
                }
            }
        }

        Class<?>[] classArr = new Class[types.length + typeList.size()];

        for (int i = 0, s = typeList.size(); i < s; i++) {
            classArr[i] = typeList.get(i);
        }
        for (int i = 0, s = types.length; i < s; i++) {
            classArr[i + s] = types[i];
        }
        // 递归获取值
        copyObject(value, o, classArr);

        return o;
    }

    /**
     * 根据实例获得泛型类型的实际类型
     *
     * @param type
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    private static LinkedList<Class<?>> getGenericityTypes(Type type, Object obj) throws IllegalAccessException {
        Class<?> class1 = obj.getClass();
        LinkedList<Class<?>> list = new LinkedList<>();

        if (type instanceof ParameterizedType) {
            // 如果这个类型是ParameterizedType这个类型，如果是class直接添加
            // 获得泛型信息
            Type rawType = ((ParameterizedType) type).getRawType();
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            list.add(class1);
            // 如果泛型类型跟实例类型一致
            if (type(rawType) == type(class1)) {
                // 如果类型是集合类型，按照集合的类型进行遍历
                if (type(class1) == COLLECTION_TYPE) {
                    Iterator<?> iterator = ((Collection<?>) obj).iterator();
                    while (iterator.hasNext()) {
                        Object next = iterator.next();
                        if (next != null) {
                            // 递归获取
                            list.addAll(getGenericityTypes(actualTypeArguments[0], next));
                            break;
                        }
                    }
                } else if (type(class1) == MAP_TYPE) {
                    // 如果是map，按照map的方式遍历，然后获取他的子泛型
                    Set<Map.Entry<?, ?>> entrySet = ((Map) obj).entrySet();
                    for (Map.Entry<?, ?> entry : entrySet) {
                        Object key = entry.getKey();
                        Object value = entry.getValue();
                        if (key != null || value != null) {
                            // 递归调用，将Key的类型泛型集合返回
                            list.addAll(getGenericityTypes(actualTypeArguments[0], key));
                            // 递归调用，Value的类型泛型集合返回
                            list.addAll(getGenericityTypes(actualTypeArguments[1], value));
                            break;
                        }
                    }
                }
            } else {
                throw new IllegalAccessException();
            }
        } else {
            list.add((Class<?>) type);
        }

        return list;
    }

    /**
     * 判断是否是基本类型对象的包装类型或者包装类型对应的基本类型
     *
     * @param c1
     * @param c2
     * @return
     */
    private static boolean typeEquals(Class<?> c1, Class<?> c2) {
        boolean sign1 = isPrimitive(c1);
        boolean sign2 = isPackclass(c2);
        if (!sign1 ^ sign2) {
            if (sign1) {
                Class<?> packclass = packClassType(c1);
                return c2.equals(packclass);
            } else {
                Class<?> packclass = packClassType(c2);
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
    private static Class<?> packClassType(Class<?> c) {
        if (c.equals(int.class)) {
            return Integer.class;
        } else if (c.equals(double.class)) {
            return Double.class;
        } else if (c.equals(long.class)) {
            return Long.class;
        } else if (c.equals(short.class)) {
            return Short.class;
        } else if (c.equals(byte.class)) {
            return Byte.class;
        } else if (c.equals(char.class)) {
            return Character.class;
        } else if (c.equals(boolean.class)) {
            return Boolean.class;
        } else if (c.equals(float.class)) {
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
    private static boolean isPrimitive(Class<?> aclass) {
        return aclass.isPrimitive();
    }

    /**
     * 判断是不是包装类型（包括String类型）
     *
     * @param aclass
     * @return
     */
    private static boolean isPackclass(Class<?> aclass) {
        Class<?>[] types = { Integer.class, Double.class, Float.class, Long.class, Short.class, Byte.class,
            Boolean.class, Character.class, String.class, };

        for (Class<?> type : types) {
            if (aclass.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 首字母变大写
     *
     * @param str
     * @return
     */
    private static String initialUp(String str) {
        if (str == null || "".equals(str.trim())) {
            // 后期做异常处理
            return null;
        }
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }

    /**
     * 数组复制
     *
     * @param sour  复制源
     * @param targ  复制体
     * @param types
     */
    private static void assignArray(Class<?> dimensionType, Object sour, Object targ, Class<?>[] types) {
        // 获得复制体对象的class对象
        Class<?> tClass = targ.getClass();
        Class<?> componentType = tClass.getComponentType();
        // 获取数组长度
        int length = Array.getLength(sour);
        // 循环遍历数组
        for (int i = 0; i < length; i++) {
            // 获取数元素
            Object so = Array.get(sour, i);
            // 判断类型
            if (isPrimitive(componentType) || isPackclass(componentType)) {
                // 如果是基本类型或者包装类或者String类型
                Array.set(targ, i, so);
            } else {
                // 其他类型
                Object to = null;
                if (componentType.isArray()) {
                    // 获取还是一个数组
                    int[] lengthArr = getLengthList(componentType, so);
                    to = Array.newInstance(dimensionType, lengthArr);
                } else {
                    // 其他类
                    try {
                        to = dimensionType.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 递归调用
                copyObject(so, to, types);
                // 给复制体复制
                Array.set(targ, i, to);
            }
        }
    }

    /**
     * 获得数组维度对应的长度
     *
     * @param c
     * @param sour
     * @return
     */
    private static int[] getLengthList(Class<?> c, Object sour) {
        List<Integer> lengthList = new ArrayList<>();
        // 判断是不是一个数组
        while (c.isArray()) {
            // 获取数组长度，添加list元素
            lengthList.add(Array.getLength(sour));
            // 然后将数组元素赋值给c变量
            c = c.getComponentType();
            // 获取数组类的元素
            sour = Array.get(sour, 0);
        }
        int[] arr = null;
        // 转成int数组
        if (!lengthList.isEmpty()) {
            arr = new int[lengthList.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = lengthList.get(i);
            }
        }
        return arr;
    }

    /**
     * 获得数组类型
     *
     * @return
     */
    private static Class<?> getDimensionType(Class<?> zlass) {
        // 判断是不是一个数组反射类
        while (zlass.isArray()) {
            // 如果是，获取他的数组元素
            zlass = zlass.getComponentType();
        }
        return zlass;
    }

    // 空
    private static final byte NULL = 0;
    // 集合
    private static final byte COLLECTION_TYPE = 1;
    // Map
    private static final byte MAP_TYPE = 2;
    // 数组
    private static final byte ARRAY_TYPE = 3;
    // 其他
    private static final byte OTHER_TYPE = 4;
    // 日期
    private static final byte DATE_TYPE = 5;

    /**
     * 返回类型
     *
     * @param type
     * @return
     */
    private static byte type(Type type) {
        if (type == null) {
            return NULL;
        }
        try {
            return type(Class.forName(type.getTypeName().replaceAll("<.*>", "")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回类型
     *
     * @param clazz
     * @return
     */
    private static byte type(Class<?> clazz) {
        // 判空
        if (clazz == null) {
            return NULL;
        }
        // 如果是数组
        if (clazz.isArray()) {
            return ARRAY_TYPE;
        }
        // 获得类名
        String simpleName = clazz.getSimpleName();

        if ("List".equals(simpleName) || "Set".equals(simpleName)) {
            return COLLECTION_TYPE;
        }
        if ("Map".equals(simpleName)) {
            return MAP_TYPE;
        }
        // 如果他的所有实现的接口
        Class<?>[] superclass = clazz.getInterfaces();
        for (Class<?> c : superclass) {

            simpleName = c.getSimpleName();
            if ("List".equals(simpleName) || "Set".equals(simpleName)) {
                return COLLECTION_TYPE;
            }
            if ("Map".equals(simpleName)) {
                return MAP_TYPE;
            }
        }
        if(clazz.equals(Date.class)){
            return DATE_TYPE;
        }
        // 如果不是数组、集合、map那么就认为是一个其他对象
        return OTHER_TYPE;
    }

}
