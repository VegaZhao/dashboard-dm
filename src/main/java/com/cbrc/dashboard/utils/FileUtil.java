package com.cbrc.dashboard.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author YY
 * @version 1.0
 * @ClassName: FileUtil
 * @Description:
 * @date 2016年8月26日  上午10:26:17
 */
public class FileUtil {

    /**
     * 获取文件类型和文件名
     *
     * @param fileName
     * @return
     */
    public static String[] getFileExtName(String fileName) {
        String result[] = null;
        if (fileName != null) {
            result = new String[2];
            int i = fileName.lastIndexOf('.');
            if (i > -1) {
                result[0] = fileName.substring(0, i);
                result[1] = fileName.substring(i + 1).toLowerCase();
            } else {
                result[0] = fileName;
                result[1] = null;
            }
        }
        return result;
    }

    /**
     * 复制文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void copyFile(File file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream(filePath + fileName);

        byte[] b = new byte[1024];

        while ((fin.read(b)) != -1) {//读取到末尾 返回-1 否则返回读取的字节个数
            fout.write(b);
        }
        fin.close();
        fout.flush();
        fout.close();
    }

    /**
     * 复制文件
     *
     * @param bytes
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void copyFileByBytes(byte[] bytes, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fout = new FileOutputStream(filePath + fileName);
        fout.write(bytes);
        fout.flush();
        fout.close();
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static void fileDownload(HttpServletResponse response, File file) throws IOException {
        if (null != file && file.exists()) {
            // 配置文件下载
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 0);
            // 实现文件下载
            byte[] buffer = new byte[1024];

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }

            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}