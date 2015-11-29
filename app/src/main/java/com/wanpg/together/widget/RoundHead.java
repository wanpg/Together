package com.wanpg.together.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


import com.wanpg.together.R;
import com.wanpg.together.util.ImageManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RoundHead extends View{

	private Activity mActivity;
	public RoundHead(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RoundHead(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RoundHead(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private int mWidth,mHeight;
	@SuppressLint("NewApi")
	private void init(Context context) {
		// TODO Auto-generated method stub
		if (context instanceof Activity) {
			this.mActivity = (Activity)context;
		}
		try {
			if(android.os.Build.VERSION.SDK_INT >= 11){
				setLayerType(LAYER_TYPE_SOFTWARE, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	private Bitmap mBitmap;
	private Rect dstRect=new Rect();
	private Rect srcRect=new Rect();

	private int edgeW = 1;
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG |Paint.FILTER_BITMAP_FLAG));
		
		Paint mPaint = new Paint(); 
		mPaint.setAntiAlias(true); 
		mPaint.setColor(getResources().getColor(android.R.color.black));
		
		if(android.os.Build.VERSION.SDK_INT >= 11){
			drawDirect(canvas, mPaint, mBitmap);
		}else{
			drawIndirect(canvas, mPaint);
		}
	}
	
	private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
	
	private void drawDirect(Canvas canvas, Paint paint, Bitmap bm) {
		// TODO Auto-generated method stub
		if(bm==null)
			return;

		canvas.drawOval(new RectF(0, 0, mWidth, mHeight), paint);
		int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.save();
        
		dstRect.set(edgeW, edgeW, mWidth-edgeW, mHeight-edgeW);
		srcRect.set(0, 0, bm.getWidth(), bm.getHeight());
		RectF mRectF = new RectF(dstRect);  
		//设置圆角半径为20  
		float roundPx = mWidth/2-edgeW;  
		//先绘制圆角矩形  
		paint.setColor(Color.WHITE);
		canvas.drawRoundRect(mRectF, roundPx, roundPx, paint);  
		canvas.save();
		//设置图像的叠加模式  
		paint.setXfermode(xfermode);  
		//绘制图像  
		canvas.drawBitmap(bm, srcRect, dstRect, paint);
        canvas.restoreToCount(sc);
	}

	private void drawIndirect(Canvas canvas, Paint paint){
		Bitmap bm = null;
		bm = getRoundedBitmap(mBitmap);
		
		if(bm == null){
			return;
		}
		canvas.drawOval(new RectF(0, 0, mWidth, mHeight), paint);
		srcRect.set(0, 0, bm.getWidth(), bm.getHeight());
		dstRect.set(edgeW, edgeW, mWidth-edgeW, mHeight-edgeW);
		canvas.drawBitmap(bm, srcRect, dstRect, paint);
	}


	public int dp2px(float dpValue) {
		float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	//图片圆角处理  
	private Bitmap getRoundedBitmap(Bitmap bm) {
		if(bm==null)
			return null;
		Bitmap bgBitmap = null;
		bgBitmap = ImageManager.getBitmapByKey(key + "circle");
		if(bgBitmap!=null)
			return bgBitmap;
		//创建新的位图  
		bgBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);  
		//把创建的位图作为画板  
		Canvas c = new Canvas(bgBitmap);  
		Paint mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		Rect mRect = new Rect(0, 0, bm.getWidth(), bm.getHeight());  
		RectF mRectF = new RectF(mRect);  
		//设置圆角半径为20  
		float roundPx = bm.getWidth()/2;  
		mPaint.setAntiAlias(true);  
		//先绘制圆角矩形  
		c.drawRoundRect(mRectF, roundPx, roundPx, mPaint);  
		//设置图像的叠加模式  
		mPaint.setXfermode(xfermode);  
		//绘制图像  
		c.drawBitmap(bm, mRect, mRect, mPaint);  
		ImageManager.putBitmapToCache(bgBitmap, key + "circle");
		return bgBitmap;  
	}  

	public void setHead(String url){
		setHead(url, R.drawable.avatar_default);
	}

	private String key;
	public void setHead(final String url, final int defRes){
		key = String.valueOf(defRes);
		setHead(BitmapFactory.decodeResource(getResources(), defRes));
		if(!TextUtils.isEmpty(url)) {
			Bitmap bitmap = ImageManager.getBitmapByKey(url);
			if (bitmap != null) {
				key = url;
				setHead(bitmap);
			} else {
				new Thread(){
					@Override
					public void run() {
						final Bitmap finalBitmap = getHttpBitmap(url);
						RoundHead.this.post(new Runnable() {
							@Override
							public void run() {
								setHead(finalBitmap);
							}
						});
					}
				}.start();
			}
		}
	}

	public void setHead(Bitmap bitmap, String key){
		this.key = key  + bitmap.toString();
		setHead(bitmap);
	}

	private void setHead(Bitmap bitmap){
		mBitmap = bitmap;
		this.invalidate();
	}


	/**
	 * 获取网络图片资源
	 *
	 * @param url
	 * @return
	 */
	private Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			//获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
			//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			//连接设置获得数据流
			conn.setDoInput(true);
			//不使用缓存
			conn.setUseCaches(false);
			//这句可有可无，没有影响
			conn.connect();
			//得到数据流
			InputStream is = conn.getInputStream();
			//解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			//关闭数据流
			is.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

}
