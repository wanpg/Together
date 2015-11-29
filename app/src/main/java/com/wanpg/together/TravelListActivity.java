package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.data.CityRecomModel;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.net.Api;

import java.util.ArrayList;

public class TravelListActivity extends BaseActivity {


    private CityRecomModel cityModel;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLisView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);
        Bundle bundle = getIntent().getExtras();
        cityModel = (CityRecomModel) bundle.getSerializable("city");
        if(cityModel==null){
            onBackPressed();
            return;
        }
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_swipe_container);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(cityModel.cityName);
//        ArrayList<TravelItem> list = new ArrayList<>();
//        for(int i=0;i<10;i++){
////            list.add(TravelItem.create());
//        }
        mLisView = (ListView) findViewById(R.id.list);

         adapter = new TravelListAdapter((BaseActivity)this, mDataList);
        mLisView.setAdapter(adapter);

        mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TravelListActivity.this, TravelDetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item", mDataList.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        mRefreshLayout.setRefreshing(true);
        getList();
    }

    private TravelListAdapter adapter;


    private ArrayList<TravelItem> mDataList = new ArrayList<>();
    void getList(){
        Api.getTravelList(this, cityModel.cityName, null, null, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
                if(info!=null){
                    mDataList = (ArrayList<TravelItem>) info;
                    if(mDataList==null)
                        mDataList = new ArrayList<TravelItem>();
                    refreshUi();
                }
            }
        });
    }

    void refreshUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.updateData(mDataList);
            }
        });
    }

}
