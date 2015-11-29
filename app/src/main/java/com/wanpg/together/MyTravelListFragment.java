package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.base.BaseFragment;
import com.wanpg.together.data.Common;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.net.Api;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/25.
 */
public class MyTravelListFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private View mMainView;
    private TabLayout mTabLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLisView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mMainView==null){
            mMainView = inflater.inflate(R.layout.frag_travellist, null);
            initView();
        }

        mRefreshLayout.setRefreshing(true);
        getList();
        return mMainView;
    }


    private void initView(){
        mTabLayout = (TabLayout) mMainView.findViewById(R.id.travel_tab);
        TabLayout.Tab tab1 = mTabLayout.newTab();
        TabLayout.Tab tab2 = mTabLayout.newTab();
        TabLayout.Tab tab3 = mTabLayout.newTab();

        tab1.setText("tab1");
        tab2.setText("tab2");
        tab3.setText("tab3");


        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);
        mTabLayout.addTab(tab3);

        mTabLayout.setScrollPosition(0, 0, true);
        mTabLayout.setVisibility(View.GONE);


        mRefreshLayout = (SwipeRefreshLayout) mMainView.findViewById(R.id.list_swipe_container);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });


        mLisView = (ListView) mMainView.findViewById(R.id.list);

        adapter = new TravelListAdapter((BaseActivity) getActivity(), mDataList);
        mLisView.setAdapter(adapter);

        mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private TravelListAdapter adapter;


    private ArrayList<TravelItem> mDataList = new ArrayList<>();
    void getList(){
        Api.getTravelList(getActivity(), null, Common.mUser.id, null, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
                try {
                    if (info != null) {
                        mDataList = (ArrayList<TravelItem>) info;
                        if (mDataList == null)
                            mDataList = new ArrayList<TravelItem>();
                        refreshUi();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    void refreshUi(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.updateData(mDataList);
            }
        });
    }
}
