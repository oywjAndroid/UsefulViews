package com.oywj.usefulviews.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ProjectName:IntentService
 * Created by Tang_CW on 2015/9/23.
 * Author:Tang_CW
 * Email:smith_tang@foxmail.com
 * Date:2015/9/23
 */

/**
 * 常用IO操作的集合类，
 */
public class IOUtils {

    /**
     *  将一个字节数组写入一个文件输出流
     * @param data 所需要写的字节数组
     * @param fos  需要写入的文件输出流
     * @return 返回一个布尔值，成功返回true，失败返回false
     */
    public static boolean byteArray2OS(byte[] data, FileOutputStream fos) {

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(fos);
            bos.write(data,0,data.length);

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeStream(bos,fos);
        }
        return false;
    }

    /**
     * 从输入流中读取所有的数据，再以字节数组的形式返回
     * @param in 输入流
     * @return 返回值是一个字节数组
     */
    public static byte[] is2ByteArray(InputStream in) {

        ByteArrayOutputStream bos = null;

        try {
            bos = new ByteArrayOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = in.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeStream(bos,in);
        }
        return null;
    }


    /**
     * 流的关闭，内部通过迭代的方式，关闭流。
     * Closeable是所有流的父类
     * @param closeables 需要关闭的流的集合（可变参数）
     */
    public static  void closeStream(Closeable... closeables){
        for (int i = 0; i < closeables.length; i++) {
            if (closeables[i] != null) {
                try {
                    closeables[i].close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
