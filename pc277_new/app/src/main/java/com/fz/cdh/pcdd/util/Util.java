package com.fz.cdh.pcdd.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {

    public static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }

    public static boolean isPeopleID(String idNum) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idNum);
        return idNumMatcher.matches();
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[[0-9],\\D])|(14[[0-9],\\D])|(15[[0-9],\\D])|(16[[0-9],\\D])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 合并数组
     *
     * @return
     */
    public static String[] concat(String[] a, String[] b, String[] c) {
        String[] m = new String[a.length + b.length + c.length];
        System.arraycopy(a, 0, m, 0, a.length);
        System.arraycopy(b, 0, m, a.length, b.length);
        System.arraycopy(c, 0, m, a.length + b.length, c.length);
        return m;
    }

    public static boolean hasHoneycomb() {
        // Can use static final constants like HONEYCOMB, declared in later
        // versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 重新处理imageurl
     *
     * @param imageUrl
     * @return
     */
    public static String getSDcardImageUrl(String imageUrl) {
        String result = String.format("file:///%s", imageUrl);
        return result;
    }

    /**
     * 将转换后的图片保存到位图
     *
     * @param mBitmap
     * @param file
     * @throws IOException
     */
    public static void saveBitmap2File(Bitmap mBitmap, File file) throws IOException {
        if (mBitmap == null)
            return;

        // File f = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
        fos.flush();
        fos.close();
    }

//    /**
//     * 得到软件内部版本号
//     *
//     * @return
//     */
//    public static int getAppVersionCode() {
//        Context context = UCheDaoApplication.getContext();
//        try {
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            return pi.versionCode;
//        } catch (Exception e) {
//
//        }
//        return 0;
//    }

    /**
     * 车牌号是否有效
     *
     * @param carPlate
     * @return
     */
    public static boolean isCarPlateValid(String carPlate) {
        if (TextUtils.isEmpty(carPlate)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
        Matcher matcher = pattern.matcher(carPlate);
        return matcher.matches();
    }

    /**
     * sp转为px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return
     */
    public static String formatDataTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return formatter.format(d);
    }

    /**
     * 时间格式化
     *
     * @param dateTime
     * @return
     */
    public static long formatDataTime(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");

        try {
            Date d = formatter.parse(dateTime);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较两个字符串是否相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean stringEquals(String s1, String s2) {
        if (s1 == s2)
            return true;
        if (s1 == null)
            return false;
        return s1.equals(s2);
    }

    /**
     * 截取文件名
     */
    public static String getFileName(String path) {
        if (TextUtils.isEmpty(path))
            return null;
        int start = path.lastIndexOf("/");
        if (start != -1)
            return path.substring(start + 1);
        else
            return null;
    }

    /**
     * 获取缩略图
     *
     * @param context
     * @param uri     图片uri
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Bitmap getThumbBitmap(Context context, Uri uri) throws FileNotFoundException, IOException {
        ContentResolver resolver = context.getContentResolver();
        Uri thumb = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, uri.getLastPathSegment());

        return MediaStore.Images.Media.getBitmap(resolver, thumb);
    }

    /**
     * 得到uri的实际文件路径 Get a file path from a Uri. This will get the the path for
     * Storage Access Framework Documents, as well as the _data field for the
     * MediaStore and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 图片压缩
     *
     * @param filePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getimage(String filePath, int width, int height) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;// 不加载图片
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > width) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (w / width);
        } else if (w < h && h > height) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (h / height);
        }
        if (be <= 0)
            be = 1;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = be;// 设置缩放比例
        opt.inJustDecodeBounds = false;//
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(filePath, opt);
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static String Hanyu2Pinyin(String name) {
        String pinyinName = "";
//        char[] nameChar = name.toCharArray();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        for (int i = 0; i < nameChar.length; i++) {
//            if (nameChar[i] > 128) {
//                try {
//                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return pinyinName;
    }
}
