package com.wanpg.together.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import com.wanpg.together.R;


/**
 * Created by wanpg on 15/5/31.
 */
public abstract class TimerDialog extends Dialog {

    public TimerDialog(Context context) {
        super(context);
    }

    public TimerDialog(Context context, int theme) {
        super(context, theme);
    }

    public TimerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_timer);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        datePicker.getMonth();
//        datePicker
    }

    public abstract void onConfirmClick(String str);
//
//    public void setTitle(String title){
//        mTitleText.setVisibility(View.VISIBLE);
//        mTitleText.setText(title);
//    }
//
//    public void setContent(String content){
//        mContentText.setVisibility(View.VISIBLE);
//        mContentText.setText(content);
//    }



}
