package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.base.BaseFragment;
import com.wanpg.together.data.Common;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.data.User;
import com.wanpg.together.net.Api;
import com.wanpg.together.widget.CommentDialog;
import com.wanpg.together.widget.RoundHead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/25.
 */
public class UserInfoFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private View mMainView;
    private RoundHead head;
    private TextView name;
    private TabLayout mTabLayout;
    private ListView list1, list2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mMainView==null){
            mMainView = inflater.inflate(R.layout.frag_userinfo, null);
            mTabLayout = (TabLayout) mMainView.findViewById(R.id.travel_tab);
            TabLayout.Tab tab1 = mTabLayout.newTab();
            TabLayout.Tab tab2 = mTabLayout.newTab();

            tab1.setText("收藏");
            tab2.setText("旅伴");


            mTabLayout.addTab(tab1);
            mTabLayout.addTab(tab2);

            mTabLayout.setScrollPosition(0, 0, true);

            head = (RoundHead) mMainView.findViewById(R.id.head);

            name = (TextView) mMainView.findViewById(R.id.name);

            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    show();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            list1 = (ListView) mMainView.findViewById(R.id.list1);
            list2 = (ListView) mMainView.findViewById(R.id.list2);
            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), TravelDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("item", mDataList.get(position));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });

        }
        getList();
        bindData();
        show();
        return mMainView;
    }

    void bindData(){
        travelAdapter = new TravelListAdapter((BaseActivity) getActivity(), mDataList);
        list1.setAdapter(travelAdapter);
        list2.setAdapter(memberAdapter);
        name.setText(Common.mUser.name);
        head.setHead("", Common.mapImage.get(Common.mUser.picKey));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_me, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CommentDialog mUpdateDialog = new CommentDialog(getActivity(), R.style.about_dialog){

            @Override
            public void onConfirmClick(String str) {
                if(TextUtils.isEmpty(str)){
                    return;
                }
                Common.PHONE = str;
                Api.getUserInfo(getActivity(), Common.PHONE, "", new Api.ResultListener() {
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
        };

        mUpdateDialog.show();
        return super.onOptionsItemSelected(item);
    }

    void getList(){
        Api.getTravelList(getActivity(), null, Common.mUser.id, null, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                if (info != null) {
                    mDataList = (ArrayList<TravelItem>) info;
                    if (mDataList == null)
                        mDataList = new ArrayList<TravelItem>();
                    mUserList.clear();
                    for(TravelItem item : mDataList){
                        if(item.members!=null) {
                            for (User user : item.members){
                                if(user!=null && Common.mUser.id.equals(user.id)){
                                    mUserList.add(user);
                                }
                            }
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show();
                        }
                    });
                }
            }
        });
    }

    void show(){
        if(mTabLayout.getSelectedTabPosition()==0){
            list1.setVisibility(View.VISIBLE);
            list2.setVisibility(View.GONE);
            travelAdapter.updateData(mDataList);
        }else{
            list1.setVisibility(View.GONE);
            list2.setVisibility(View.VISIBLE);
            memberAdapter.notifyDataSetChanged();
        }
    }

    private TravelListAdapter travelAdapter;
    private ArrayList<TravelItem> mDataList = new ArrayList<>();
    private ArrayList<User> mUserList = new ArrayList<>();


    private BaseAdapter memberAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mUserList.size();
        }

        @Override
        public Object getItem(int position) {
            return mUserList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.member_item, null);
            }
            User user = mUserList.get(position);
            ((RoundHead)convertView.findViewById(R.id.head)).setHead("", Common.mapImage.get(user.picKey));
            ((TextView)convertView.findViewById(R.id.name)).setText(user.name);
            return convertView;
        }
    };
}
