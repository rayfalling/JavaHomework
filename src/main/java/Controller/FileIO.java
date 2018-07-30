package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * 文件IO操作类
 * 全局默认覆写模式
 * 修改文件方法为定位pos复写
 *
 * @author 海蔚
 * @version 0.1
 */
public class FileIO {
    /**
     * 获取文件操作句柄
     */
    private static File getFileHandle(String path) {
        return new File(path);
    }

    /**
     * 获取输入流
     *
     * @param path 文件路径
     * @return 文件输入流
     */
    private static FileInputStream getInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(Objects.requireNonNull(getFileHandle(path).canRead() ? getFileHandle(path) : null));
    }

    /**
     * 获取输出流
     *
     * @param path 文件路径
     * @return 文件输出流
     */
    private static FileOutputStream getOutputStream(String path) throws FileNotFoundException {
        return new FileOutputStream(Objects.requireNonNull(getFileHandle(path).canRead() ? getFileHandle(path) : null), true);
    }

    public static String getSuffix(String path) {
        return isFile(path) ? path.substring(path.lastIndexOf(".") + 1) : null;
    }

    private static boolean isFile(String path) {
        return path.lastIndexOf(".") != -1;
    }

    /**
     * 创建前置目录
     * 防止出现找不到目录错误
     *
     * @param path 要检查的完整路径
     * @return 创建成功返回true
     */
    public static boolean CreatePreDirectory(String path) {
        if (getFileHandle(path).exists()) {
            return true;
        } else {
            if (!isFile(path) && getFileHandle(path).mkdir())
                return true;
            else {
                return CreatePreDirectory(path.substring(0, path.lastIndexOf("/")));
            }
        }
    }

    /**
     * 公共写文件方法
     *
     * @param buffer    要写入的字节
     * @param overwrite 是否复写文件
     * @param path      文件路径
     */
    public static boolean WriteFile(String buffer, String path, boolean overwrite) {
        try {
            path = Class.class.getClass().getResource("/").toURI().getPath().replaceFirst("/", "") + path;
            if (!getFileHandle(path).exists())
                if (CreatePreDirectory(path) && !getFileHandle(path).createNewFile())
                    return false;
            FileOutputStream fos = getOutputStream(path);
            byte[] bytes;
            FileInputStream fis = getInputStream(path);
            int length = fis.available();
            fis.close();
            if (overwrite) {
                bytes = new byte[length > buffer.getBytes().length ? length : buffer.getBytes().length];
                System.arraycopy(buffer.getBytes(), 0, bytes, 0, buffer.getBytes().length);
                fos.write(bytes, 0, bytes.length);
            } else
                fos.write(buffer.getBytes(), length + 1, buffer.getBytes().length);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 公共读文件
     *
     * @param path 文件路径
     */
    public static String ReadFile(String path) {
        try {
            path = Class.class.getClass().getResource("/").toURI().getPath().replaceFirst("/", "") + path;
            if (getFileHandle(path).exists()) {
                FileInputStream fis = getInputStream(path);
                byte[] bytes = new byte[fis.available()];
                if (fis.read(bytes) == -1) return null;
                return new String(bytes);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 递归删除文件
     */
    public boolean Delete(String path) {
        if (getFileHandle(path).exists()) {
            if (getFileHandle(path).isFile())
                return getFileHandle(path).delete();
            else {
                if (getFileHandle(path).isDirectory()) {
                    for (File f : Objects.requireNonNull(getFileHandle(path).listFiles())) {
                        return Delete(f.getAbsolutePath());
                    }
                }
            }
        }
        return false;
    }
}