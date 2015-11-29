package com.wanpg.together.data;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class Message implements Serializable{
    public String id, content,user_id,created, post_id;
    public static Message create(JSONObject jo){
        Message message = new Message();
        message.id = jo.optString("id");
        message.content = jo.optString("content");
        message.user_id = jo.optString("user_id");
        message.created = jo.optString("created");
        message.post_id = jo.optString("post_id");
        return message;
    }
}
