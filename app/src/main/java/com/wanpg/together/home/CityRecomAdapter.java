package com.wanpg.together.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.R;
import com.wanpg.together.data.CityRecomModel;
import com.wanpg.together.data.DisplayData;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class CityRecomAdapter extends BaseAdapter {

    private BaseActivity activity;
    private ArrayList<CityRecomModel> list;
    public CityRecomAdapter(BaseActivity activity, ArrayList<CityRecomModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CityRecomModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(activity).inflate(R.layout.city_recom_item, null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayData.dp2px(250));
            convertView.setLayoutParams(lp);
        }
        CityRecomModel model = list.get(position);
        ((ImageView) convertView.findViewById(R.id.image)).setImageResource(model.iconRes);
        ((TextView)convertView.findViewById(R.id.name)).setText(model.cityName);
        return convertView;
    }

    class ViewHolder{

    }
}
