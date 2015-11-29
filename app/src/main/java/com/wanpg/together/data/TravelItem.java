package com.wanpg.together.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class TravelItem implements Serializable {
    public String src_location,
            dest_location,
            mid_locations,
            start,
            end,
            travel_type,
            person_all,
            person_have,
            pic_key,
            level,
            title,
            desc, id, user_id;

    public ArrayList<User> members;
    public ArrayList<Comment> comments;

    public static TravelItem create(JSONObject jsonObject) {
        JSONObject jo = jsonObject.optJSONObject("post");
        JSONArray jaUser = jsonObject.optJSONArray("members");
        JSONArray jaComment = jsonObject.optJSONArray("comments");
        TravelItem item = new TravelItem();
        item.src_location=jo.optString("src_location");
        item.dest_location=jo.optString("dest_location");
        item.mid_locations=jo.optString("mid_locations");
        item.start=jo.optString("start");
        item.end=jo.optString("end");
        item.travel_type=jo.optString("travel_type");
        item.person_all=jo.optString("person_all");
        item.person_have=jo.optString("person_have");
        item.pic_key=jo.optString("pic_key");
        item.level=jo.optString("level");
        item.title=jo.optString("title");
        item.desc=jo.optString("desc");
        item.id=jo.optString("id");
        item.user_id = jo.optString("user_id");
        item.members = new ArrayList<>();
        item.comments = new ArrayList<>();
        for(int i=0;i<jaUser.length();i++){
            item.members.add(User.createByJson(jaUser.optJSONObject(i)));
        }
        for(int i=0;i<jaComment.length();i++){
            item.comments.add(Comment.create(jaComment.optJSONObject(i)));
        }
        return item;
    }

    public static TravelItem createOnly(JSONObject jo) {
        TravelItem item = new TravelItem();
        item.src_location=jo.optString("src_location");
        item.dest_location=jo.optString("dest_location");
        item.mid_locations=jo.optString("mid_locations");
        item.start=jo.optString("start");
        item.end=jo.optString("end");
        item.travel_type=jo.optString("travel_type");
        item.person_all=jo.optString("person_all");
        item.person_have=jo.optString("person_have");
        item.pic_key=jo.optString("pic_key");
        item.level=jo.optString("level");
        item.title=jo.optString("title");
        item.desc=jo.optString("desc");
        item.id=jo.optString("id");
        item.user_id = jo.optString("user_id");
        return item;
    }
}
