package com.gmm.www.librarytest;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/3/26
 * @类说明:
 */
public class ShellApplication extends Application {
    public static String getPassword() {
        return "abcdefghijklmnop";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        AES.init(getPassword());
        File apkFile = new File(getApplicationInfo().sourceDir);
        //在data/data/包名/files/fake_apk 创建解压目录
        File unzipFile = getDir("fake_apk",MODE_PRIVATE);
        File app = new File(unzipFile,"app");
        if (!app.exists()) {
            //1.解压apk，放到app目录下
            Zip.unZip(apkFile,app);
            File[] files = app.listFiles();
            for (File file :
                    files) {
                String name = file.getName();
                //2.将加密的源dex文件进行解密
                if (name.endsWith("classes.dex")) {//这个dex是壳dex，不用处理

                } else if (name.endsWith(".dex")){//这些dex文件都是apk中加过密的源dex文件
                    try {
                        //把文件转化成字节数组byte[]
                        byte[] bytes = getBytes(file);
                        //将这些字节数组进行解密
                        byte[] decrypt =  AES.encrypt(bytes);
                        //将解码的字节数组，再写回到dex文件中，进行替换
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(decrypt);
                        fos.flush();
                        fos.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            List<File> list = new ArrayList<>();
            for (File file :
                    app.listFiles()) {
                if (file.getName().endsWith(".dex")){
                    list.add(file);
                }
            }

            //通过反射，将所有的dex文件生成对应的elements[]数组添加到系统的dexElements[]中，让ClassLoader可以加载这些dex文件（与热修复原理相同）
            try {
                V19.install(getClassLoader(),list,unzipFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


    private static final class V19 {
        private V19() {

        }

        /**
         * 利用反射，获取ClassLoader中的 属性Field，方法Method
         * @param classLoader 类加载器
         * @param additionalClassPathEntries  dex文件集合
         * @param optimizedDirectory
         */
        private static void install(ClassLoader classLoader,List<File> additionalClassPathEntries
                        ,File optimizedDirectory) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

            //获取 classloader 中的 pathList属性
            Field pathListField = findField(classLoader,"pathList");
            //获得 pathList 属性的 值(对象)
            Object dexPathList = pathListField.get(classLoader);
            ArrayList suppressedExceptions = new ArrayList();
            if (Build.VERSION.SDK_INT >= 23) {//android 6.0以上
                expandFieldArray(dexPathList,"dexElements",makePathElements(dexPathList,
                        new ArrayList(additionalClassPathEntries),optimizedDirectory,suppressedExceptions));
            } else {
                expandFieldArray(dexPathList,"dexElements",makeDexElements(dexPathList,
                        new ArrayList(additionalClassPathEntries),optimizedDirectory,suppressedExceptions));
            }

            if (suppressedExceptions.size() > 0) {
                Iterator suppressedExceptionsField = suppressedExceptions.iterator();

                while (suppressedExceptionsField.hasNext()) {
                    IOException dexElementsSuppressedExceptions = (IOException)
                            suppressedExceptionsField.next();
                    Log.w("MultiDex", "Exception in makeDexElement",
                            dexElementsSuppressedExceptions);
                }

                Field suppressedExceptionsField1 = findField(classLoader,
                        "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions1 = (IOException[]) ((IOException[])
                        suppressedExceptionsField1.get(classLoader));
                if (dexElementsSuppressedExceptions1 == null) {
                    dexElementsSuppressedExceptions1 = (IOException[]) suppressedExceptions
                            .toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[suppressedExceptions.size() +
                            dexElementsSuppressedExceptions1.length];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions1, 0, combined,
                            suppressedExceptions.size(), dexElementsSuppressedExceptions1.length);
                    dexElementsSuppressedExceptions1 = combined;
                }

                suppressedExceptionsField1.set(classLoader, dexElementsSuppressedExceptions1);
            }
        }
    }

    /**
     * 获取属性
     * @param instance  属性所属类的实例对象
     * @param fieldName  属性的名字
     * @return
     */
    private static Field findField(Object instance,String fieldName) throws NoSuchFieldException {
        //①获得Class对象
        Class clazz = instance.getClass();

        while (clazz != null) {
            try {
                //getDeclaredField 只获取本类定义的属性
                Field field = clazz.getDeclaredField(fieldName);
                if (!field.isAccessible()) {
                    //如果该属性是私有的，需要setAccessible(true)，这样才可以获取这个属性的值value
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                //当前类中没有此属性，再找父类中有没有该属性
                clazz = clazz.getSuperclass();
            }
        }
        //如果没有找到该属性，抛出异常
        throw new NoSuchFieldException("Field " + fieldName + " not found in " + instance.getClass());
    }

    /**
     * 获取方法
     * @param instance 方法所属类的实例对象
     * @param methodName 方法名
     * @param parameterTypes 方法中所有传参的类型
     * @return
     */
    private static Method findMethod(Object instance,String methodName, Class... parameterTypes) throws NoSuchMethodException {
        Class clazz = instance.getClass();

        while (clazz != null) {
            try {
                //getDeclaredMethod 只获取本类定义的方法
                Method method = clazz.getDeclaredMethod(methodName,parameterTypes);
                //如果方法是私有的，设置方法可访问
                if (!method.isAccessible()){
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                //当前类中没有此方法，再找父类中有没有该方法
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchMethodException("Method " + methodName + " not found in " + instance.getClass());
    }

    /**
     * 目的是将解压和加密后的dex文件通过反射得到element[]数组插入到系统的dexElements数组中
     * @param instance dexPathList对象
     * @param fieldName  dexElements
     * @param extraElements  自己反射生成的 element[] 数组
     */
    private static void expandFieldArray(Object instance,String fieldName,Object[]
                                         extraElements) throws NoSuchFieldException, IllegalAccessException {
        //从dexPathList中 获取 dexElements 数组属性，
        Field jlrField = findField(instance,fieldName);
        //系统的elements[]
        Object[] original = (Object[]) jlrField.get(instance);
        //合并的elements[]
        Object[] combined = (Object[]) Array.newInstance(original.getClass());
        //合并系统的dexElements[]和我们生产的dexElements
        System.arraycopy(original,0,combined,0,original.length);
        System.arraycopy(extraElements,0,combined,original.length,extraElements.length);

        //将合并好的Elements[]数组 重新赋值给 系统的dexElements
        jlrField.set(instance,combined);
    }

    /**
     * 在6.0以上 sdk_int>=23的系统里，DexPathList中里有makePathElements()方法够钱element[]数组，
     * 所以我们可以通过反射调用DexPathList的makePathElements()来创建含有我们解密dex文件的element[]
     * @param dexPathList  DexPathList的实例对象
     * @param files  makePathElements()需要的传参
     * @param optimizedDirectory makePathElements()需要的传参
     * @param suppressedExceptions makePathElements()需要的传参
     * @return
     * @throws NoSuchMethodException
     */
    private static Object[] makePathElements(Object dexPathList,
                                            ArrayList<File> files,
                                            File optimizedDirectory,
                                            ArrayList<IOException> suppressedExceptions) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method makePathElements = findMethod(dexPathList,"makePathElements",new Class[]{ArrayList.class,File.class,ArrayList.class});

        /**
         * invoke 参数一：所属类的实例对象
         *        参数二：方法需要的传参
         */
        return (Object[]) makePathElements.invoke(dexPathList,new Object[]{files,optimizedDirectory,suppressedExceptions});
    }

    /**
     * 在6.0以下 sdk_int<23的系统里，DexPathList中里有makeDexElements()方法够钱element[]数组，
     * 所以我们可以通过反射调用DexPathList的makeDexElements()来创建含有我们解密dex文件的element[]
     * @param dexPathList  DexPathList的实例对象
     * @param files  makePathElements()需要的传参
     * @param optimizedDirectory makePathElements()需要的传参
     * @param suppressedExceptions makePathElements()需要的传参
     * @return
     * @throws NoSuchMethodException
     */
    private static Object[] makeDexElements(Object dexPathList,
                                            ArrayList<File> files, File
                                                    optimizedDirectory,
                                            ArrayList<IOException> suppressedExceptions) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method makeDexElements = dexPathList.getClass().getDeclaredMethod("makeDexElements"
                ,new Class[]{ArrayList.class,File.class,ArrayList.class});
        return (Object[]) makeDexElements.invoke(dexPathList,new Object[]{files,optimizedDirectory,suppressedExceptions});
    }

    /**
     * 将文件转换成字节 byte[]
     * @param file
     * @return
     */
    public byte[] getBytes(File file) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file,"r");
        byte[] bytes = new byte[(int) raf.length()];
        raf.readFully(bytes);
        raf.close();
        return bytes;
    }
}
