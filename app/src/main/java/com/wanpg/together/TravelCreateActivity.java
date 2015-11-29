package com.wanpg.together;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.data.Common;
import com.wanpg.together.net.Api;
import com.wanpg.together.widget.Notice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class TravelCreateActivity extends BaseActivity {


    private EditText mStartPosEt, mEndPosEt, mMidPosEt, mCurPeoNumEt, mLeftPeoEt, mDescEt;
    private TextView mStartTime,mEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle("发布约贴");
        mStartPosEt = (EditText) findViewById(R.id.text_cur_pos);
        mEndPosEt = (EditText) findViewById(R.id.text_target_pos);
        mMidPosEt = (EditText) findViewById(R.id.text_custom_pos);
        mCurPeoNumEt = (EditText) findViewById(R.id.spinner_has);
        mLeftPeoEt  = (EditText) findViewById(R.id.spinner_left);
        mStartTime = (TextView) findViewById(R.id.text_start_time);
        mEndTime = (TextView) findViewById(R.id.text_end_date);
        mDescEt = (EditText) findViewById(R.id.desc);
        final Calendar calendar = Calendar.getInstance();

        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TravelCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        //更新EditText控件日期 小于10加0
                        mStartTime.setText(new StringBuilder().append(year).append("-")
                                .append((month + 1) < 10 ? 0 + (month + 1) : (month + 1))
                                .append("-")
                                .append((day < 10) ? 0 + day : day));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ).show();
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(TravelCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        //更新EditText控件日期 小于10加0
                        mEndTime.setText(new StringBuilder().append(year).append("-")
                                .append((month + 1) < 10 ? 0 + (month + 1) : (month + 1))
                                .append("-")
                                .append((day < 10) ? 0 + day : day));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_ok){
            createTravel();
        }
        return super.onOptionsItemSelected(item);
    }

    void createTravel(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("src_location", mStartPosEt.getText().toString());
        map.put("dest_location", mEndPosEt.getText().toString());
        map.put("mid_locations", mMidPosEt.getText().toString());
        map.put("start", mStartTime.getText().toString());
        map.put("end", mEndTime.getText().toString());
        map.put("travel_type", Common.TRAVEL_TYPE[1]);

        int mCurnum = Integer.parseInt(mCurPeoNumEt.getText().toString());
        int mLeftnum = Integer.parseInt(mLeftPeoEt.getText().toString());

        map.put("person_all", mCurnum + mLeftnum);
        map.put("person_have", mCurnum);

        String[] arr = new String[]{ "changlelinchang", "hongkong", "huihanggudao", "jianglangshan", "laojianzhu", "linmandedao", "shanghai", "suzhouyuanlin", "xuexiang"};
        int pos = (int)(Math.random() * 9);
        map.put("pic_key", arr[pos]);
        map.put("level", Common.COST_TYPE[1]);
        map.put("user_id", Common.mUser.id);
        map.put("title", "一起去旅行吧");
        map.put("desc", mDescEt.getText().toString());
        Api.addTravel(this, map, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                Log.d("wanpg", info.toString());
                if(info!=null) {
                    try {
                        JSONObject jo = new JSONObject(info.toString());
                        boolean res = jo.optBoolean("success");
                        if(res){
                            showToast("行程添加成功！");
                            finish();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showToast("行程添加失败！");
            }
        });
    }


    void showToast(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Notice.showToast(str);
            }
        });
    }
}
