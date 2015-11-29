package com.wanpg.together.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wanpg.together.R;


/**
 * Created by wanpg on 15/5/31.
 */
public abstract class CommentDialog extends Dialog {

    public CommentDialog(Context context) {
        super(context);
    }

    public CommentDialog(Context context, int theme) {
        super(context, theme);
    }

    public CommentDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private TextView mTitleText;
    private EditText mContentText;
    private Button mCancelBtn, mConfirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);

        mTitleText = (TextView) findViewById(R.id.tv_dialog_title);
        mContentText = (EditText) findViewById(R.id.tv_dialog_content);
        mTitleText.setVisibility(View.VISIBLE);
        mContentText.setVisibility(View.VISIBLE);

        mCancelBtn = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirmBtn = (Button) findViewById(R.id.btn_dialog_confirm);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog.this.dismiss();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmClick(mContentText.getText().toString());
                CommentDialog.this.dismiss();
            }
        });
    }

    public abstract void onConfirmClick(String str);

    public void setTitle(String title){
        mTitleText.setVisibility(View.VISIBLE);
        mTitleText.setText(title);
    }

    public void setContent(String content){
        mContentText.setVisibility(View.VISIBLE);
        mContentText.setText(content);
    }



}
