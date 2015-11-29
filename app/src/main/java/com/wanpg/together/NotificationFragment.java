package com.wanpg.together;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wanpg.together.base.BaseFragment;
import com.wanpg.together.data.Common;
import com.wanpg.together.data.Message;
import com.wanpg.together.data.TravelItem;
import com.wanpg.together.data.User;
import com.wanpg.together.net.Api;
import com.wanpg.together.widget.RoundHead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangjinpeng on 15/11/25.
 */
public class NotificationFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    SwipeRefreshLayout swipeRefreshLayout;
    ListView mLisView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(swipeRefreshLayout==null){
            swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.frag_notice, null);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getList();
                }
            });
            mLisView = (ListView) swipeRefreshLayout.findViewById(R.id.list);

            mLisView.setAdapter(adapter);

            mLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), TravelDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("item", listTravel.get(position));
                    b.putString("post_id", listTravel.get(position).id);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }

        swipeRefreshLayout.setRefreshing(true);
        getList();
        return swipeRefreshLayout;
    }

    ArrayList<Message> listMsg = new ArrayList<>();
    ArrayList<TravelItem> listTravel = new ArrayList<>();

    void getList(){
        Api.getMessageList(getActivity(), Common.mUser.id, new Api.ResultListener() {
            @Override
            public void onResult(int status, Object info) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                if(info!=null){
                    try {
                        JSONArray ja = new JSONArray(info.toString());
                        listMsg.clear();
                        listTravel.clear();
                        for(int i=0;i<ja.length();i++){
                            JSONObject jo = ja.optJSONObject(i);
                            if(jo!=null){
                                listMsg.add(Message.create(jo.optJSONObject("message")));
                                listTravel.add(TravelItem.createOnly(jo.optJSONObject("post")));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                refreshUi();
            }
        });
    }

    void refreshUi(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }


    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listMsg.size();
        }

        @Override
        public Object getItem(int position) {
            return listMsg.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.message_item, null);
                viewHolder = new ViewHolder();
                viewHolder.head = (RoundHead) convertView.findViewById(R.id.head);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.content = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Message message = listMsg.get(position);
            TravelItem item = listTravel.get(position);

            String keyHead = "";
            if(item.members!=null){
                for(User user : item.members){
                    if(message.user_id.equals(user.id)){
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

            viewHolder.head.setHead("");
            viewHolder.content.setText(message.content);
            viewHolder.title.setText(item.title);
            return convertView;
        }
    };

    class ViewHolder{
        RoundHead head;
        TextView title,content;
    }
}
