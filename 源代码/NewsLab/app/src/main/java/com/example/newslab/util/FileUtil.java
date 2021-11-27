package com.example.newslab.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    public static String saveBitmap(String fileName, Bitmap bitmap, Context context) {
        String path = context.getFilesDir() + "/images/";
        if(FileUtil.isFileExist(path)) {
            File file = new File(path, fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                return path + fileName;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String saveBitmapByCurrentTime(Bitmap bitmap, Context context) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "temp_" + sdf.format(now) + ".jpg";
        return FileUtil.saveBitmap(fileName, bitmap, context);
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        if(file.exists())
            return true;
        else
            return file.mkdir();
    }
}
