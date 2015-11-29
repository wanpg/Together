package com.wanpg.together;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanpg.together.base.BaseActivity;
import com.wanpg.together.data.Common;
import com.wanpg.together.data.DisplayData;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.data.User;
import com.wanpg.together.widget.RoundHead;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class TravelListAdapter extends BaseAdapter {

    private  ArrayList<TravelItem> list;
    private BaseActivity mActivity;
    public TravelListAdapter(BaseActivity activity, ArrayList<TravelItem> list) {
        this.list = list;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.travel_item, null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayData.dp2px(210));
            convertView.setLayoutParams(lp);
            viewHolder = new ViewHolder();
            viewHolder.head = (RoundHead) convertView.findViewById(R.id.head);
            viewHolder.textDate = (TextView) convertView.findViewById(R.id.text_date);
            viewHolder.textNum = (TextView) convertView.findViewById(R.id.text_num);
            viewHolder.textPos = (TextView) convertView.findViewById(R.id.text_pos);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TravelItem item = list.get(position);

        viewHolder.textDate.setText(item.start + " ~ " + item.end);
        viewHolder.textPos.setText(item.src_location + "->" + item.dest_location);
        viewHolder.textNum.setText("上限" + item.person_all+"人" + "   已有" + item.person_have + "人");
        String key = item.pic_key;
        int res = Common.mapImage.get(key);
        if(res!=0){
            viewHolder.imageView.setImageResource(res);
        }

        String keyHead = "";
        if(item.members!=null){
            for(User user : item.members){
                if(item.user_id.equals(user.id)){
                    keyHead = user.picKey;
                    break;
                }
            }
        }

        viewHolder.head.setHead("");
        if(!TextUtils.isEmpty(keyHead)) {
            int res1 = Common.mapImage.get(keyHead);
            if (res1 != 0) {
                viewHolder.head.setHead("", res1);
            }
        }

        return convertView;
    }


    class ViewHolder{
        RoundHead head;
        ImageView imageView;
        TextView textPos,textDate,textNum;
    }

    public void updateData(ArrayList<TravelItem> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
