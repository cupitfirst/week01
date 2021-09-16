package com.bh1ofp;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class XlassLoader extends ClassLoader{
    public static void main(String[] args){
        try {
            Class<?> clazz =  new XlassLoader().findClass("Hello.xlass");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            for (Method m : clazz.getDeclaredMethods()) {
                //打印类内的方法
                System.out.println(clazz.getSimpleName() + "." + m.getName());
                //调用类方法
                m.invoke(instance);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /***
     * 根据加密字节码文件返回类定义
     * @param fileName 加密字节码文件名
     * @return 解密后的字节码类定义
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String fileName) throws ClassNotFoundException{

        byte[] bytes = null;
        InputStream ips;
        String suffix = ".xlass";
        try {
            ips = new FileInputStream("src/com/bh1ofp/" + fileName);
            bytes = decode(ips.readAllBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(fileName.replace(suffix, ""),bytes,0,bytes.length);
    }

    /**
     * 解码，y=255-x
     * @param byteArray 2进制数组
     * @return 解码后2进制数组
     */
    public byte[] decode(byte[] byteArray){
        byte[] decodeBytes = new byte[byteArray.length];
        for(int i = 0; i< byteArray.length; i++){
            decodeBytes[i] = (byte) (255-byteArray[i]);
        }
        return decodeBytes;
    }
}
