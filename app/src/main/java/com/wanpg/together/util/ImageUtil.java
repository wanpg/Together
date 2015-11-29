package com.wanpg.together.util;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtil {
	/**
	 * 回收bitmap
	 *
	 * @param bm
	 */
	public static void recycleBitmap(Bitmap bm) {
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
			bm = null;
		}
	}

	public static Bitmap addBitmapShadow(Bitmap originalBitmap) {
		BlurMaskFilter blurFilter = new BlurMaskFilter(2, BlurMaskFilter.Blur.OUTER);

		Paint shadowPaint = new Paint();

		shadowPaint.setMaskFilter(blurFilter);

		int[] offsetXY = new int[6];

		Bitmap shadowBitmap = originalBitmap.extractAlpha(shadowPaint, offsetXY);


		Bitmap shadowImage32 = shadowBitmap.copy(Bitmap.Config.ARGB_8888, true);

		Canvas c = new Canvas(shadowImage32);
		//originalBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth()+6, originalBitmap.getHeight()+6, true);
		c.drawBitmap(originalBitmap, 2, 2, null);

		return shadowImage32;
	}


	public static boolean bitmapToFile(Bitmap bitmap, String savePath) {          
		if(bitmap==null)
			return false;
		File aFile = new File(savePath);
		try {      
			ByteArrayOutputStream baos = new ByteArrayOutputStream();      
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);      
			byte[] photoBytes = baos.toByteArray();      

			if (aFile.exists()) {      
				aFile.delete();      
			}      
			aFile.createNewFile();      

			FileOutputStream fos = new FileOutputStream(aFile);      
			fos.write(photoBytes);      
			fos.flush();      
			fos.close();      

			return true;      
		} catch (Exception e1) {      
			e1.printStackTrace();      
			if (aFile.exists()) {      
				aFile.delete();      
			}      
			return false;      
		}   
	}

	public static Bitmap viewToBitmap(View v){
		v.clearFocus();//currentView表示设置的View对象 
		v.setPressed(false); 
		v.setDrawingCacheBackgroundColor(0); 
		v.setDrawingCacheEnabled(true); 
		Bitmap viewBitmap = v.getDrawingCache(); 
		v.setDrawingCacheEnabled(false); 
		return viewBitmap;
	}

}
