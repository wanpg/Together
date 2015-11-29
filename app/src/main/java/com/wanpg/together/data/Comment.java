package com.wanpg.together.data;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class Comment implements Serializable{

    public String id, user_id, post_id, content, created, user_name;

    public static Comment create(JSONObject jo){
        if(jo==null)
            return null;
        Comment comment = new Comment();
        comment.id = jo.optString("id");
        comment.user_id = jo.optString("user_id");
        comment.post_id = jo.optString("post_id");
        comment.content = jo.optString("content");
        comment.created = jo.optString("created");
        comment.user_name = jo.optString("user_name");
        return comment;
    }
}
