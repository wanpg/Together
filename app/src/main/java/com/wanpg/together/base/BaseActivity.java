package com.wanpg.together.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wanpg.together.data.CityData;
import com.wanpg.together.data.DisplayData;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayData.init(this);
        CityData.getInstance().init(this);
    }
}
