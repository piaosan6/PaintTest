package com.pjm.painttest.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;


import com.pjm.painttest.MyApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FileUtils {

    private static final String FILE_TEST_DIR = "test";
    public static String AUTH_PATH = "com.pjm.painttest";


    public static void initFileDir(){
        createPath(FILE_TEST_DIR);
    }

    /**
     * 创建文件， 如果不存在则创建，否则返回原文件的File对象
     * @param path 文件路径
     * @return 创建好的文件对象, 返回为空表示失败
     */
    public static synchronized File createFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (file.isFile()) {
            return file;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && (parentFile.isDirectory() || parentFile.mkdirs())) {
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getSystemAlbumPath() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + File.separator + "test";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public static Uri getUri(Context context,String url){
        File tempFile = new File(url);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            try{
                return FileProvider.getUriForFile(context, AUTH_PATH, tempFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            return Uri.fromFile(tempFile);
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileDir, String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        File file = new File(fileDir,  fileName);
        if (file.exists()){
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        }else
            status = false;
        return status;
    }

    public static String getPathFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (Objects.requireNonNull(uri.getScheme())) {
            case "content":
                return getPathFromContentUri(uri, context);
            case "file":
                return  uri.getPath();
            default:
                return null;
        }
    }

    public static File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (Objects.requireNonNull(uri.getScheme())) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    private static String getPathFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath = "";
        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        }
        return filePath;
    }

    private static File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String fileName;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            fileName = cursor.getString(cursor.getColumnIndex(filePathColumn[1]));
            cursor.close();
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
            if(file != null){
                if (!file.exists() || file.length() <= 0 || TextUtils.isEmpty(filePath)) {
                    filePath = getPathFromInputStreamUri(context, contentUri, fileName);
                }
                if (!TextUtils.isEmpty(filePath)) {
                    file = new File(filePath);
                }
            }
        }
        return file;
    }

    /**
     * 用流拷贝文件一份到自己APP目录下
     */
    public static String getPathFromInputStreamUri(Context context, Uri uri, String fileName) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File file = createTemporalFileFrom(context, inputStream, fileName);
                filePath = file.getPath();

            } catch (Exception e) {
                L.e(e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    L.e(e);
                }
            }
        }
        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream, String fileName) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];
            //自己定义拷贝文件路径
            targetFile = new File(getCacheDir(context), fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile;
    }

    public static String getBasePath(){
        String path = getSDCardPath() + "/assistant";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    private static String getCacheDir(Context context) {
        return context.getCacheDir().getPath();
    }


    public static String getSDCardPath(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }else {
            return MyApp.getInstance().getCacheDir().getPath();
        }
    }

    public static String getPicPath() {
        String picturePath = getBasePath();
        createPath(picturePath);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return picturePath + "IMG_" + timeStamp + ".jpg";
    }

    /**
     * 判断传入的地址是否已经有这个文件夹，没有的话需要创建
     */
    private static void createPath(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    // /data/user/0/appName/files
    public static String getFileDir(Context mContext){
        return mContext.getFilesDir().getAbsolutePath();
    }

    public static String getDownLoadFile(Context mContext, String deviceSn){
        return mContext.getFilesDir().getAbsolutePath() + File.separator + deviceSn;
    }


    /*
     * 实现文件的拷贝
     * @param srcPathStr    源文件的地址信息
     * @param desPathStr    目标文件的地址信息
     */
    public static void copyFile(String srcPathStr, String desPathStr) {
        try{
            L.e("srcPathStr = " + srcPathStr + ", desPathStr = " + desPathStr);
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);
            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fis.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
            File file = new File(desPathStr);
            if(file.exists()){
                //删除未拷贝完成的文件
                file.delete();
            }
        }
    }

    /**
     * 复制文件
     * @param filename 文件名
     * @param bytes 数据
     */
    public static void copy(String filename, byte[] bytes) {
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream output = new FileOutputStream(filename);
                output.write(bytes);
                L.i("copy: success，" + filename);
                output.close();
            } else {
                L.i("copy:fail, " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyAssetsToSd(Context context, String srcPath, String dstPath) {
        try {
            File outFile = new File(dstPath);
            InputStream is = context.getAssets().open(srcPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射调用获取内置存储和外置sd卡根路径(通用)
     * @param mContext    上下文
     * @param is_removale 是否可移除，false返回内部存储路径，true返回外置SD卡路径
     *                    如果没有位置的SD卡路径，将返回内置的存储路径
     * @return
     */
    private static String getStoragePath(Context mContext, boolean is_removale) {
        String path = "";
        //使用getSystemService(String)检索一个StorageManager用于访问系统存储功能。
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);

            for (int i = 0; i < Array.getLength(result); i++) {
                Object storageVolumeElement = Array.get(result, i);
                path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getLogoPath() {
        return getSDCardPath() + "/";
    }

    public static Long getChatLongId(String idStr) {
        long id = 0;
        String s = idStr.replace("chat_", "");
        try {
            id = Long.parseLong(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

}
