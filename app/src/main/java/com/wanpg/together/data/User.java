package com.wanpg.together.data;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class User implements Serializable{

    public String id;
    public String phoneNum;
    public String name;
    public String picKey;

    public static User create(String id, String phoneNum, String name, String picKey){
        User user = new User();
        user.id = id;
        user.phoneNum = phoneNum;
        user.name = name;
        user.picKey = picKey;
        return user;
    }

    public static User createByJson(JSONObject jo){
        if(jo==null)
            return null;
        return create(jo.optString("id"), jo.optString("phone"), jo.optString("name"),jo.optString("pic_key"));
    }
}
