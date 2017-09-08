package com.fz.cdh.pcdd.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class BitmapTool {

	public static Bitmap zoom(Bitmap bitmap, float zf) {
		Matrix matrix = new Matrix();
		matrix.postScale(zf, zf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
		// RCB means
		// Rounded
		// Corner Bitmap
		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstbmp);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return dstbmp;
	}
	
	public static int dp2px(Context context, float dp)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	
	public static int px2dp(Context context, float px)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	public static Bitmap getCircularBitmap(Bitmap sourceBitmap){
		int targetWidth = sourceBitmap.getWidth();
		int targetHeight = sourceBitmap.getHeight();
		Bitmap targetBitmap = Bitmap.createBitmap(
		targetWidth,
		targetHeight,
		Config.ARGB_8888);
		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(
		((float)targetWidth - 1) / 2,
		((float)targetHeight - 1) / 2,
		(Math.min(((float)targetWidth), ((float)targetHeight)) / 2),
		Path.Direction.CCW);
		canvas.clipPath(path);
		canvas.drawBitmap(
		sourceBitmap,
		new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
		new Rect(0, 0, targetWidth, targetHeight),
		null);
	
		return targetBitmap;

	}
	
	public static Bitmap bytes2Bitmap(byte[] b) {
		if(b.length != 0)
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		else
			return null;
	}
	
	public static byte[] Bitmap2Bytes(Bitmap img) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		float scale = Math.min(1, Math.min(600f / img.getWidth(), 600f / img.getHeight()));
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		Bitmap imgSmall = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
				img.getHeight(), matrix, false);
		// Log.v(TAG, "imgSmall size : " + imgSmall.getWidth() + " " +
		// imgSmall.getHeight());

		imgSmall.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] array = stream.toByteArray();
		return array;
	}
	
	public static File Bitmap2File(Context context, Bitmap img) {
		File file = null;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			file = new File(context.getFilesDir()+"/temp"+System.currentTimeMillis());
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(Bitmap2Bytes(img));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return file;
	}
	
	/**
     * 将转换后的图片保存到位图
     *	true 成功	false 失败
     */
    public static boolean Bitmap2File(Bitmap mBitmap, String filePath) {
        if (mBitmap == null)
            return false;

        try {
        	File f = new File(filePath);
	        if (!f.exists()) {
	            f.createNewFile();
	        }
	        FileOutputStream fos = new FileOutputStream(f);
	        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
	        fos.flush();
	        fos.close();
	        return true;
        } catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
    /**
     * 保存图片至手机图库
     */
    public static boolean saveBitmap2Local(Context context, Bitmap mBitmap, String filePath) {
    	if(Bitmap2File(mBitmap, filePath)) {
    		try {
    			File f = new File(filePath);
    			MediaStore.Images.Media.insertImage(context.getContentResolver(), 
    					f.getAbsolutePath(), f.getName(), null);
				// 最后通知图库更新
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + f.getAbsolutePath())));
    			return true;
    		} catch(Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
	public static void saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
	//	context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
	}
	public static int getScreenWidthPX(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	public static int getScreenHeightPX(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	public static Bitmap File2Bitmap(Context context, File f) {
		return compressBitmapFromFile(context, f.getAbsolutePath());
	}
	
	public static Drawable Bitmap2Drawable(Context context, Bitmap bm) {
		return new BitmapDrawable(context.getResources(), bm);
	}
	
	public static Bitmap compressBitmapFromFile(Context context, String srcPath) {
		BitmapFactory.Options newOp = new BitmapFactory.Options();
		newOp.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOp);
		
		newOp.inJustDecodeBounds = false;
		int w = newOp.outWidth;
		int h = newOp.outHeight;
		int ww = getScreenWidthPX(context);
		int hh = getScreenHeightPX(context);
		int be = 1;
		if(w>h && w>ww)
			be = w / ww;
		else if(w<h && h>hh)
			be = h / hh;
		newOp.inSampleSize = be;
		newOp.inPreferredConfig = Config.ARGB_8888;
		newOp.inPurgeable = true;
		newOp.inInputShareable = true;
		return BitmapFactory.decodeFile(srcPath, newOp);
	}
	
	/**
	 * 以最省内存的方式读取本地资源的图片
	 */
	public static Bitmap readBitmap(Context context, int resId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	
	/**
	 * 创建QR二维码图片
	 */
	public static Bitmap createQRCodeBitmap(String content) {
		// 用于设置QR二维码参数
		Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别——这里选择最高H级别
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		// 生成QR二维码数据——这里只是得到一个由true和false组成的数组
		// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, 300, 300, qrParam);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
			int w = bitMatrix.getWidth();
			int h = bitMatrix.getHeight();
			int[] data = new int[w * h];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (bitMatrix.get(x, y))
						data[y * w + x] = 0xff000000;// 黑色
					else
						data[y * w + x] = -1;// -1 相当于0xffffffff 白色
				}
			}

			// 创建一张bitmap图片，采用最高的效果显示
			Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			// 将上面的二维码颜色数组传入，生成图片颜色
			bitmap.setPixels(data, 0, w, 0, 0, w, h);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
