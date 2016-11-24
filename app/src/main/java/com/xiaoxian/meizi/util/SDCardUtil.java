package com.xiaoxian.meizi.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD卡辅助类
 * Created by Administrator on 2016/11/4.
 */

public class SDCardUtil {
    private SDCardUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断SDCard是否可用
     * @return
     */
    public static boolean isSDCardEnable() {
        /**
         * getExternalStorageState()获取外部存储当前状态
         * MEDIA_MOUNTED表示sd卡正常挂载，可读可写
         */
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     * @return
     */
    public static String getSDCardPath() {
        /**
         * getExternalStorageDirectory()获取外部存储目录
         * File.separator表示路径分隔符(相当于/)
         */
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡剩余容量，单位byte
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            // StatFs用于获取文件系统状态，能够获取sd卡大小和剩余空间
            // 获取以block为单位
            StatFs sf = new StatFs(getSDCardPath());
            // 获取空闲数据块数量(不懂为何减4)，API18以上getAvailableBlocks()换成getAvailableBlocksLong()
            long availableBlocks = sf.getAvailableBlocksLong() - 4;
            // 获取单个数据块大小(byte)，API18以上getBlockSize()换成getBlockSizeLong()
            long blockSize = sf.getBlockSizeLong();
            return availableBlocks * blockSize;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间剩余可用容量字节数，单位byte
     * @param filePath
     * @return
     */
    public static long getFreeBytes(String filePath) {
        /**
         * 如果是sd卡路径，则获取sd卡可用容量
         * startsWith()：String类方法，检测某字符串是否以另一字符串开始
         */
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
            /**
             * 如果是内部存储路径，则获取内存存储可用容量
             * 获取数据目录
             */
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs sf = new StatFs(filePath);
        long availableBlocks = sf.getAvailableBlocksLong() - 4;
        long blockSize = sf.getBlockSizeLong();
        return availableBlocks * blockSize;
    }

    /**
     * 获取系统存储路径
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
