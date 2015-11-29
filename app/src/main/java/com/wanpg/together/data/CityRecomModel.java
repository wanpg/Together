package com.wanpg.together.data;

import java.io.Serializable;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class CityRecomModel implements Serializable{
    public String cityName;
    public int iconRes;

    public static CityRecomModel create(String name, int res){
        CityRecomModel model = new CityRecomModel();
        model.cityName = name;
        model.iconRes = res;
        return model;
    }
}
