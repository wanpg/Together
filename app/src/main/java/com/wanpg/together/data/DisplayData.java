package com.wanpg.together.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplayData {
	
	private static DisplayMetrics mDisplayMetrics;
	public static void init(Context context){
		if(mDisplayMetrics==null){
			mDisplayMetrics = context.getResources().getDisplayMetrics();
		}
	}
	
    /**
     * 获取设备密度
     *
     
     * @return
     */
	public static float getDensity() {
        return mDisplayMetrics.density;
    }
	
    /**
     * 获取设备密度DPI
     *
     * 
     * @return
     */
    public static int getDensityDpi() {
        return mDisplayMetrics.densityDpi;
    }
	
	/**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * 
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final float scale = getDensity();
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * 
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = getDensity();
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度--dp值
     *
     * 
     * @return
     */
    public static int getDisWidthDp() {
        int widthPx = getDisWidth();
        return px2dp(widthPx);
    }

    /**
     * 获取屏幕高度--dp值
     *
     * 
     * @return
     */
    public static int getDisHeightDp() {
        int heightPx = getDisHeight();
        return px2dp(heightPx);
    }    
    
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getDisWidth() {
        return mDisplayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getDisHeight() {
        return mDisplayMetrics.heightPixels;
    }
    

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static float[] getWindowSize(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display dis = wm.getDefaultDisplay();
		float[] f = new float[2];
		if(android.os.Build.VERSION.SDK_INT >= 13){
			Point p = new Point();
			dis.getSize(p);
			f[0] = p.x;
			f[1] = p.y;
		}else{
			f[0] = dis.getWidth();
			f[1] = dis.getHeight();
		}
		return f;
	}
    
    /**
     * 获得状态栏的高度
     *
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return statusHeight;
    }
}
