package com.wanpg.together.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.base.BaseFragment;
import com.wanpg.together.CitySearchActivity;
import com.wanpg.together.R;
import com.wanpg.together.TravelCreateActivity;
import com.wanpg.together.TravelListActivity;
import com.wanpg.together.data.CityRecomModel;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/25.
 */
public class HomeFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private ArrayList<CityRecomModel> list;
    private View mMainView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mMainView==null){
            mMainView = inflater.inflate(R.layout.frag_home, null);
            initView();
        }
        return mMainView;
    }

    private void initView(){
        FloatingActionButton fab = (FloatingActionButton) mMainView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TravelCreateActivity.class));
            }
        });

        listView = (ListView) mMainView.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) mMainView.findViewById(R.id.list_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        list = new ArrayList<>();
        list.add(CityRecomModel.create("杭州市", R.drawable.hangzhou));
        list.add(CityRecomModel.create("大理市", R.drawable.dali));
        list.add(CityRecomModel.create("丹麦", R.drawable.danmai));
        list.add(CityRecomModel.create("婺源市", R.drawable.huangling));
        list.add(CityRecomModel.create("牡丹江市", R.drawable.xuexiang));
        list.add(CityRecomModel.create("绍兴市", R.drawable.yushandao));

        CityRecomAdapter adapter = new CityRecomAdapter((BaseActivity) getActivity(), list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityRecomModel model = (CityRecomModel) listView.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("city", model);
                Intent intent = new Intent(getActivity(), TravelListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
//            Notice.showToast("跳转搜索界面");
            startActivity(new Intent(getActivity(), CitySearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
