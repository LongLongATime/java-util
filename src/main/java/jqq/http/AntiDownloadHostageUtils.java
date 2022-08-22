package jqq.http;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class AntiDownloadHostageUtils {

    // 默认读取文件开头，默认10k
    public static final int BEGIN_SIZE = Configurations.AntiDownloadHostage.BEGIN_SIZE;
    // 默认读取文件结尾，默认10k
    public static final int END_SIZE = Configurations.AntiDownloadHostage.END_SIZE;

    /**
     * 对文件全文生成MD5摘要，形式为小写字母
     *
     * @param filePath 要加密的文件路径
     *
     * @return MD5摘要码
     */
    public static String getFileShortMD5(String filePath) {
        return getFileShortMD5(filePath, BEGIN_SIZE);
    }

    /**
     * 对文件全文生成MD5摘要，形式为小写字母
     *
     * @param filePath 要加密的文件路径
     *
     * @return MD5摘要码
     */
    public static String getFileShortMD5(String filePath, int reqSize) {
        if (reqSize <= 0) {
            throw new IllegalArgumentException(filePath + " ,the reqSize must gt zero");
        }

        if (filePath == null || filePath.length() == 0) {
            throw new IllegalArgumentException(filePath + " ,the filePath can't be null or empty!");
        }

        try {
            byte[] content = readFileContent(filePath, reqSize, true);
            if (content == null || content.length == 0) {
                return "";
            }

            String md5Value = encodeByMd5(content);
            return md5Value;
        } catch (Throwable e) {
            return "";
        }
    }

    /**
     * 获取文件尾部指定字节数的CRC32校验和
     *
     * @param filePath
     *
     * @return crc32 sum, -1 如果发生异常
     */
    public static String getFileCRC32Sum(String filePath) {
        return getFileCRC32Sum(filePath, END_SIZE);
    }

    /**
     * 获取文件尾部指定字节数的CRC32校验和
     *
     * @param filePath
     *
     * @return crc32 sum, -1 如果发生异常
     */
    public static String getFileCRC32Sum(String filePath, int reqSize) {
        if (reqSize <= 0) {
            throw new IllegalArgumentException(filePath + " ,the reqSize must gt zero");
        }

        if (filePath == null || filePath.length() == 0) {
            throw new IllegalArgumentException(filePath + " ,the filePath can't be null or empty!");
        }

        try {
            byte[] content = readFileContent(filePath, reqSize, false);
            if (content == null || content.length == 0) {
                return "";
            }

            String crc32Sum = calCRC32Sum(content);
            return crc32Sum;
        } catch (Throwable e) {
            return "";
        }
    }

    /**
     * 对指定数组计算器crc32内容sum
     *
     * @param content
     */
    public static String calCRC32Sum(byte[] content) {
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("the content can't be null or lenght equals zero");
        }
        return calCRC32Sum(content, 0, content.length);
    }

    /**
     * 对指定数组计算器crc32内容sum
     *
     * @param content
     * @param off
     * @param len
     */
    public static String calCRC32Sum(byte[] content, int off, int len) {
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("the content can't be null or lenght equals zero");
        }
        CRC32 crc = new CRC32();
        crc.update(content, off, len);
        return Long.toString(crc.getValue());
    }

    /**
     * 通过一个数组巧妙地读取指定大小的内容
     *
     * @param filePath
     * @param reqSize
     * @param isFromHead true 从头部开始读取，false 从尾部开始读取
     *
     * @return 指定内容的数组
     */
    private static byte[] readFileContent(String filePath, int reqSize,
            boolean isFromHead) {
        RandomAccessFile raf = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }

            long fileLen = file.length();
            if (fileLen <= 0) {
                return null;
            }

            raf = new RandomAccessFile(file, "r");
            if (!isFromHead && fileLen > reqSize) {
                raf.seek(fileLen - reqSize);
            }

            int len;
            if (fileLen > reqSize) {
                len = reqSize;
            } else {
                len = (int) fileLen;
            }

            byte[] buffer = new byte[len];
            int length = -1;
            int total = 0;
            while ((length = raf.read(buffer, total, len - total)) != -1) {
                total += length;
                if (total == len) {
                    break;
                }
            }
            return buffer;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 进行MD5编码，可取某小段返回
     *
     * @param bytes
     */
    private static String encodeByMd5(byte[] bytes) {

        try {
            MessageDigest md5;
            md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(bytes);
            byte[] byteArray = md5.digest();
            return Utility.byteToHexStr(byteArray);
        } catch (NoSuchAlgorithmException e) {
            SystemLogger.systemLogger.error("encodeByMd5 error!", e);
        }
        return "";
    }

}
