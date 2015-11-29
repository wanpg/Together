package com.wanpg.together.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wanpg.together.R;


/**
 * Created by wanpg on 15/5/31.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private TextView mTitleText, mContentText;
    private Button mCancelBtn, mConfirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        mTitleText = (TextView) findViewById(R.id.tv_dialog_title);
        mContentText = (TextView) findViewById(R.id.tv_dialog_content);
        mTitleText.setVisibility(View.GONE);
        mContentText.setVisibility(View.GONE);

        mCancelBtn = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirmBtn = (Button) findViewById(R.id.btn_dialog_confirm);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.this.dismiss();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setTitle(String title){
        mTitleText.setVisibility(View.VISIBLE);
        mTitleText.setText(title);
    }

    public void setContent(String content){
        mContentText.setVisibility(View.VISIBLE);
        mContentText.setText(content);
    }

}
