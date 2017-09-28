package com.riven.loggerdemo;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * File Helper
 *
 * @author Riven
 * Email : riven.zheng@knowswift.com
 */
public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String FILE_NAME = "myLogger.txt";
    private static final String CHARSET = "UTF-8";
    private static final String SPLIT_DATA = "00002017092809170000";
    private static String FILE_PATH = Environment.getExternalStorageDirectory().getPath();

    public static void createFile() {
        File file = new File(FILE_PATH, FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Log.e(TAG, "Create File Successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "    " + file.getName() + " has already existed");
            Log.e(TAG, "    " + "File Path : " + file.getAbsolutePath());
            Log.e(TAG, "    " + "File size : " + file.length() + " byte");
            Log.e(TAG, "    " + "Whether File can be wrote : " + file.canWrite());
            Log.e(TAG, "    " + "Whether File can be read : " + file.canRead());
            Log.e(TAG, "    " + "Whether File is hide : " + file.isHidden());
        }
    }

    public static void write(String s) {
        createFile();
        writeString(FILE_PATH + "/" + FILE_NAME, SPLIT_DATA + s, CHARSET);
    }

    public static void deleteFile() {
        File file = new File(FILE_PATH, FILE_NAME);
        if (file.exists()) {
            try {
                file.delete();
                Log.e(TAG, "Deleted file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getFileDir(String filePath, String type) {
        List<String> picList = new ArrayList<>();
        try {
            File f = new File(filePath);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath(), type)) {
                    picList.add(file.getPath());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //return pic address list
        return picList;
    }

    // check pic format
    private boolean checkIsImageFile(String fName, String type) {
        boolean isImageFile = false;
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals(type)) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * write data into file
     *
     * @param filePath
     * @param data
     * @return true is successful  false is failure
     */
    public static boolean writeBytes(String filePath, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath, true);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * read data from file
     *
     * @param file
     * @return
     */
    public static byte[] readBytes(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param file    file path
     * @param content the content written
     * @param charset which charset
     */
    public static void writeString(String file, String content, String charset) {
        try {
            byte[] data = content.getBytes(charset);
            writeBytes(file, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file    file path
     * @param charset which charset
     * @return string
     */
    public static String readString(String file, String charset) {
        createFile();
        byte[] data = readBytes(file);
        String ret = null;
        try {
            ret = new String(data, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<String> readEnCodeData() {
        List<String> strings = new ArrayList<>();
        for (String s : readOriginalData()) {
            try {
                strings.add(Base64.encodeToString(s.getBytes(CHARSET), Base64.DEFAULT));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }


    public static List<String> readOriginalData() {
        List<String> strings = new ArrayList<>();
        String[] tempData = readString(FILE_PATH + "/" + FILE_NAME, CHARSET).split(SPLIT_DATA);
        for (int i = 0; i < tempData.length; i++) {
            strings.add(tempData[i]);
        }
        return strings;
    }
}