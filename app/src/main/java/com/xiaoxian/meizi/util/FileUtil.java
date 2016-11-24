package com.xiaoxian.meizi.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件操作工具类
 * Created by Administrator on 2016/11/4.
 */

public class FileUtil {
    /**
     * 指定位置创建指定文件
     * @param filePath 完整文件路径
     * @param mkdir 是否创建相关的文件夹
     * @throws Exception
     */
    public static void mkFile(String filePath, boolean mkdir) throws Exception {
        File file = new File(filePath);
        // mkdirs()创建多个目录，mkdir()创建单个目录
        if (mkdir) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        file = null;
    }

    /**
     * 指定位置创建文件夹
     * @param dirPath 文件夹路径
     * @return 创建成功，返回True；反之，返回False
     */
    public static boolean mkDir(String dirPath) {
        return new File(dirPath).mkdirs();
    }

    /**
     * 删除指定文件
     * @param filePath 文件路径
     * @return 删除成功，返回True；反之，返回False
     */
    public static boolean delFile(String filePath) {
        return new File(filePath).delete();
    }

    /**
     * 删除指定文件夹
     * @param dirPath 文件夹路径
     * @param delFile 文件夹中是否包含文件
     * @return 删除成功，返回True；反之，返回False
     */
    public static boolean delDir(String dirPath, boolean delFile) {
        if (delFile) {
            File file = new File(dirPath);
            if (file.isFile()) {
                return file.delete();
            } else if (file.isDirectory()) {
                if (file.listFiles().length == 0) {
                    return file.delete();
                } else {
                    int length = file.listFiles().length;
                    // File数组，包含路径
                    File[] listFiles = file.listFiles();
                    for (int i = 0; i < length; i++) {
                        if (listFiles[i].isDirectory()) {
                            delDir(listFiles[i].getAbsolutePath(), true);
                        }
                        listFiles[i].delete();
                    }
                    return file.delete();
                }
            } else {
                return false;
            }
        } else {
            return new File(dirPath).delete();
        }
    }

    /**
     * 复制文件/文件夹 若要进行文件夹复制，勿将目标文件夹置于源文件夹中
     * @param source 源文件（夹）
     * @param target 目标文件（夹）
     * @param isFolder 若进行文件夹复制，则为True；反之，则为False
     * @throws Exception
     */
    public static void copy(String source, String target, boolean isFolder) throws Exception {
        if (isFolder) {
            (new File(target)).mkdirs();
            File f = new File(source);
            // 文件名数组
            String[] files = f.list();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                if (source.endsWith(File.separator)) {
                    temp = new File(source + files[i]);
                } else {
                    temp = new File(source + File.separator + files[i]);
                }
                if (temp.isFile()) {
                    // 获取文件内容
                    FileInputStream fis = new FileInputStream(temp);
                    // 写入文件
                    FileOutputStream fos = new FileOutputStream(target + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024];
                    int len;
                    // 读取“文件输入流”b.length个字节数组
                    while ((len = fis.read(b)) != -1) {
                        /**
                         * write(byte[] buffer, int byteOffset, int byteCount)
                         * 将buffer写入“文件输出流”，从buffer的byteOffset开始，写入长度byteCount
                         */
                        fos.write(b, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    fis.close();
                }
                if (temp.isDirectory()) {
                    copy(source + "/" + files[i], target + "/" + files[i], true);
                }
            }
        } else {
            int len1;
            File f1 = new File(source);
            if (f1.exists()) {
                InputStream is = new FileInputStream(source);
                File file = new File(target);
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fos1 = new FileOutputStream(file);
                byte[] b1 = new byte[1024];
                while ((len1 = is.read(b1)) != -1) {
                    fos1.write(b1, 0, len1);
                }
                is.close();
                fos1.close();
            }
        }
    }

    /**
     * 移动指定文件（夹）到目标文件（夹）
     * @param source 源文件（夹）
     * @param target 目标文件（夹）
     * @param isFolder 若为文件夹，则为True；反之，则为False
     * @return
     * @throws Exception
     */
    public static boolean move(String source, String target, boolean isFolder) throws Exception {
        copy(source, target, isFolder);
        if (isFolder) {
            return delDir(source, true);
        } else {
            return delFile(source);
        }
    }

    /**
     * 获取缓存文件夹
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath;
        //isExternalStorageEmulated()设备外存是否是用内存模拟，是则返回true
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageEmulated()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }
}
