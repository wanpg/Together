package com.wanpg.together.base;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.wanpg.together.data.Common;
import com.wanpg.together.data.User;
import com.wanpg.together.net.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangjinpeng on 15/11/25.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Api.getUserInfo(this, Common.PHONE, "", new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                if(info!=null){
                    String res = info.toString();
                    if(!TextUtils.isEmpty(res)){
                        try {
//                            [{"id":"5","name":"\u82cf\u514b","phone":"13500000000","password":"123456","pic_key":null}]
                            JSONArray ja = new JSONArray(res);
                            JSONObject jo = ja.optJSONObject(0);
                            Common.mUser = User.create(jo.optString("id"), jo.optString("phone"), jo.optString("name"), jo.optString("pic_key"));
                            Log.d("wanpg", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

}
