package com.wanpg.together;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.data.Comment;
import com.wanpg.together.data.Common;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.data.User;
import com.wanpg.together.net.Api;
import com.wanpg.together.widget.CommentDialog;
import com.wanpg.together.widget.Notice;
import com.wanpg.together.widget.RoundHead;

import java.util.ArrayList;

public class TravelDetailActivity extends BaseActivity {

    View viewChat, viewAddGroup, viewComment;
    RoundHead mHead;
    private TravelItem dataItem;
    TextView textDesc,textTime,textXingcheng,textNum,textCost,textTravelType,textAdd;
    LinearLayout comment_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("详情");

        Bundle b = getIntent().getExtras();
        String post_id = b.getString("post_id");
        dataItem = (TravelItem) b.getSerializable("item");

        viewChat = findViewById(R.id.view_chat);
        viewAddGroup = findViewById(R.id.view_add);
        viewComment = findViewById(R.id.view_comment);

        viewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notice.showToast("功能马上上线，敬请期待！");
            }
        });

        viewAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMeInPost()){
                    exit();
                }else{
                    join();
                }
            }
        });

        viewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog mUpdateDialog = new CommentDialog(TravelDetailActivity.this, R.style.about_dialog){

                    @Override
                    public void onConfirmClick(String str) {
                        sendComment(str);
                    }
                };
                mUpdateDialog.show();
            }
        });

        mHead = (RoundHead) findViewById(R.id.head);

        textDesc = (TextView) findViewById(R.id.desc);
        textTime = (TextView) findViewById(R.id.text_time);
        textXingcheng = (TextView) findViewById(R.id.text_xingcheng);
        textNum = (TextView) findViewById(R.id.text_person_num);
        textCost = (TextView) findViewById(R.id.text_cost_type);
        textTravelType = (TextView) findViewById(R.id.text_travel_type);
        textAdd = (TextView) findViewById(R.id.text_add);

        comment_list = (LinearLayout) findViewById(R.id.comment_list);
        bindData();
        if(!TextUtils.isEmpty(post_id)){
            getList();
        }
    }

    boolean isMinePost(){
        return Common.mUser.id.equals(dataItem.user_id);
    }


    void bindData(){
        textDesc.setText(dataItem.desc);
        textTime.setText(dataItem.start);
        textXingcheng.setText(dataItem.src_location + "->" + dataItem.dest_location);
        textNum.setText("总数"+ dataItem.person_all+ "   已有" + dataItem.person_have);
        textCost.setText(Common.COST_TYPE[0]);
        textTravelType.setText(Common.TRAVEL_TYPE[0]);

        if(isMinePost()){
            viewAddGroup.setVisibility(View.GONE);
        }else{
            viewAddGroup.setVisibility(View.VISIBLE);

            if(isMeInPost()){
                textAdd.setText("取消");
            }else{
                textAdd.setText("结伴");
            }
        }

        if(dataItem.comments!=null) {
            comment_list.removeAllViews();
            for (Comment comment : dataItem.comments) {
                if(comment!=null) {
                    View view = getLayoutInflater().inflate(R.layout.comment_name, null);

                    ((TextView) view.findViewById(R.id.text)).setText(comment.user_name + ":"+comment.content);
                    comment_list.addView(view);
                }
            }


        }

        String keyHead = "";
        if(dataItem.members!=null){
            for(User user : dataItem.members){
                if(dataItem.user_id.equals(user.id)){
                    keyHead = user.picKey;
                    break;
                }
            }
        }
        mHead.setHead("");
        if(!TextUtils.isEmpty(keyHead)) {
            int res1 = Common.mapImage.get(keyHead);
            if (res1 != 0) {
                mHead.setHead("", res1);
            }
        }

        String key = dataItem.pic_key;
        int res = Common.mapImage.get(key);
        if(res!=0){
            ((ImageView)findViewById(R.id.image)).setImageResource(res);
        }
    }

    boolean isMeInPost(){
        if(dataItem.members==null || dataItem.members.size()<=0){
            return false;
        }
        for(User user : dataItem.members){
            if(Common.mUser.id.equals(user.id)){
                return true;
            }
        }
        return false;
    }

    void getList(){
        Api.getTravelList(this, null, null, dataItem.id, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                if(info!=null){
                    ArrayList<TravelItem> items = (ArrayList<TravelItem>) info;
                    if(items!=null && items.size()>0){
                        dataItem = items.get(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bindData();
                            }
                        });
                    }
                }
            }
        });
    }

    void join(){
        Api.joinTravel(this, Common.mUser.id, dataItem.id, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                getList();
            }
        });
    }
    void exit(){
        Api.exitTravel(this, Common.mUser.id, dataItem.id, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                getList();
            }
        });
    }


    void sendComment(String str){
        Api.comment(this, Common.mUser.id, dataItem.id, str, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                getList();
            }
        });
    }

}
