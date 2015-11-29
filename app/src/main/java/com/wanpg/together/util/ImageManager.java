package com.wanpg.together.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;


import com.wanpg.together.base.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;


public class ImageManager {

	private static HashMap<String, SoftReference<Bitmap>> mImageCacheMap = new HashMap<String, SoftReference<Bitmap>>();
	
	public static Bitmap getBitmapByKey(String key) {
		if(TextUtils.isEmpty(key))
			return null;
		Bitmap bitmap = null;
		SoftReference<Bitmap> ref = mImageCacheMap.get(key);
		if (ref != null) {
			bitmap = ref.get();
			if (bitmap != null && !bitmap.isRecycled()) {
				return bitmap;
			}
		}
		String path = getSavePath(key);
		bitmap = BitmapFactory.decodeFile(path);
		if (bitmap != null) {
			mImageCacheMap.put(key, new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}
	
	public static Bitmap getBitmapByPath(String path) {
		if(TextUtils.isEmpty(path))
			return null;
		Bitmap bitmap = null;
		SoftReference<Bitmap> ref = mImageCacheMap.get(path);
		if (ref != null) {
			bitmap = ref.get();
			if (bitmap != null && !bitmap.isRecycled()) {
				return bitmap;
			}
		}
		bitmap = BitmapFactory.decodeFile(path);
		if (bitmap != null) {
			mImageCacheMap.put(path, new SoftReference<Bitmap>(bitmap));
		}
		return bitmap;
	}
	
	public static void saveBitmapToFile(Bitmap bitmap, String key){
		
		if (bitmap == null || bitmap.isRecycled() || TextUtils.isEmpty(key)) 
			return;
		
		if (!DeviceUtil.isSDCardAvailable()) 
			return;
		
		String path = getSavePath(key);
		File file = new File(path);
		
		FileUtil.createFolderIfNotExist(file.getParentFile());

		if(file.exists())
			return;

		FileUtil.createFileIfNotExist(file);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	
	public static void putBitmapToCache(Bitmap bitmap, String key){
		SoftReference<Bitmap> srf = new SoftReference<Bitmap>(bitmap);
		if(srf!=null)
			mImageCacheMap.put(key, srf);
	}
	
	
	private static String IMAGE_PATH =  "";
	private static String getSavePath(String key){
		if(TextUtils.isEmpty(IMAGE_PATH))
			IMAGE_PATH = BaseApplication.getInstance().getExternalCacheDir().getPath() + "/images";
		return IMAGE_PATH + "/" + MD5Util.string2MD5(key) + ".pngsp";
	}
	
}
