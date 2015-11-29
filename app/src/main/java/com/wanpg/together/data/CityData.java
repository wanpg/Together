package com.wanpg.together.data;

import android.content.Context;
import android.text.TextUtils;

import com.wanpg.together.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class CityData {
    private static CityData instance;

    public static CityData getInstance(){
        if(instance==null){
            instance = new CityData();
        }
        return instance;
    }


    private HashMap<Integer, PosModel> provinceMap = new HashMap<>();
    private HashMap<Integer, PosModel> cityMap = new HashMap<>();
    private HashMap<Integer, PosModel> districtMap = new HashMap<>();
    public CityData() {
    }

    private boolean isParseed = false;
    public void init(final Context context){
        if(isParseed){
            return;
        }
        isParseed = true;
        new Thread(){
            @Override
            public void run() {

                new AreaParser(context, new AreaParser.OnParseEndListener() {
                    @Override
                    public void onEnd(HashMap<Integer, PosModel> map) {
                        provinceMap = map;
                    }
                }).parse((context.getResources().openRawResource(R.raw.provinces)));
                new AreaParser(context, new AreaParser.OnParseEndListener() {
                    @Override
                    public void onEnd(HashMap<Integer, PosModel> map) {
                        cityMap = map;
                    }
                }).parse((context.getResources().openRawResource(R.raw.cities)));
                new AreaParser(context, new AreaParser.OnParseEndListener() {
                    @Override
                    public void onEnd(HashMap<Integer, PosModel> map) {
                        districtMap = map;
                    }
                }).parse((context.getResources().openRawResource(R.raw.districts)));
            }
        }.start();
    }


    public ArrayList<PosModel> getCityByName(String name){
        if(TextUtils.isEmpty(name)){
            return null;
        }
        Iterator iterator = cityMap.entrySet().iterator();
        ArrayList<PosModel> folderList = new ArrayList<PosModel>();
        while (iterator.hasNext()){
            Map.Entry<Integer, PosModel> entry = (Map.Entry<Integer, PosModel>) iterator.next();
            long key = entry.getKey();
            PosModel item = entry.getValue();
            if(item!=null){
                if(item.name.contains(name)){
                    folderList.add(item);
                }
            }
        }
        return folderList;
    }
}
