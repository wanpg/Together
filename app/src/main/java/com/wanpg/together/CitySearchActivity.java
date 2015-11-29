package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.data.CityData;
import com.wanpg.together.data.CityRecomModel;
import com.wanpg.together.data.PosModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitySearchActivity extends BaseActivity {


    ListView listView;
    View viewInfo;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.list);
        listView.setVisibility(View.GONE);

        viewInfo = findViewById(R.id.info);
        viewInfo.setVisibility(View.VISIBLE);
        et = (EditText) findViewById(R.id.et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //此处进行城市的搜索
                String str = s.toString();
                refreshList(str);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PosModel pm = searchList.get(position);
                CityRecomModel model = CityRecomModel.create(pm.name, R.drawable.ic_topic_default);
                Bundle bundle = new Bundle();
                bundle.putSerializable("city", model);
                Intent intent = new Intent(CitySearchActivity.this, TravelListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private ArrayList<PosModel> searchList;
    void refreshList(String name){
        searchList = CityData.getInstance().getCityByName(name);
        if(searchList==null || searchList.size()<=0){
            listView.setVisibility(View.GONE);
            viewInfo.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            viewInfo.setVisibility(View.GONE);

            ArrayList<Map<String, String>> listData = new ArrayList<>();
            for(PosModel pm : searchList){
                Map<String,String> map = new HashMap<>();
                map.put("name", pm.name);
                listData.add(map);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listData, R.layout.search_name, new String[]{"name"}, new int[]{R.id.text});
            listView.setAdapter(simpleAdapter);
        }
    }
}
