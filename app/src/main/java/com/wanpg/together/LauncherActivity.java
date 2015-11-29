package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wanpg.together.base.BaseActivity;


public class LauncherActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_launcher);
		startCountDown();
	}
	
	private void startCountDown() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				changeActivity();
			}
		}, 2000);
	}
	
	private void changeActivity() {
		// TODO Auto-generated method stub
		//此处判断登录状态
		Intent i = new Intent();
			i.setClass(this, MainActivity.class);
		this.startActivity(i);
		this.finish();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//do nothing,for this page cannot press back button
	}
}
