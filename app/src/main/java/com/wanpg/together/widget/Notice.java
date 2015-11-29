package com.wanpg.together.widget;

import android.text.TextUtils;
import android.widget.Toast;

import com.wanpg.together.base.BaseApplication;


public class Notice {

	private static Toast mToast;
	public static void showToast(String info){
		if(TextUtils.isEmpty(info))
			return ;
		if(mToast==null){
			mToast = Toast.makeText(BaseApplication.getInstance(), "", Toast.LENGTH_SHORT);
		}
//		mToast.cancel();
		mToast.setText(info);
		int duation = Toast.LENGTH_SHORT;
		if(info.length()>15)
			duation = Toast.LENGTH_LONG;		
		mToast.setDuration(duation);
		mToast.show();
	}
}
