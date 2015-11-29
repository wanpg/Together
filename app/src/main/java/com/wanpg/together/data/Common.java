package com.wanpg.together.data;

import com.wanpg.together.R;

import java.util.HashMap;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class Common {

    public static String PHONE = "13500000000";

    public static final String[] TRAVEL_TYPE = new String[]{"徒步","骑行","自驾","火车","汽车","飞机","邮轮"};
    public static final String[] COST_TYPE = new String[]{"穷游", "舒适", "轻奢", "豪华" };

    public static User mUser;

    public static HashMap<String, Integer> mapImage = new HashMap<>();

    static {
        mapImage.put("dali", R.drawable.dali);
        mapImage.put("danmai", R.drawable.danmai);
        mapImage.put("huangling", R.drawable.huangling);
        mapImage.put("nuowei", R.drawable.nuowei);
        mapImage.put("xuexiang", R.drawable.xuexiang);
        mapImage.put("yushandao", R.drawable.yushandao);
        mapImage.put("changlelinchang", R.drawable.changlelinchang);
        mapImage.put("hongkong", R.drawable.hongkong);
        mapImage.put("huihanggudao", R.drawable.huihanggudao);
        mapImage.put("jianglangshan", R.drawable.jianglangshan);
        mapImage.put("laojianzhu", R.drawable.laojianzhu);
        mapImage.put("linmandedao", R.drawable.linmandedao);
        mapImage.put("shanghai", R.drawable.shanghai);
        mapImage.put("suzhouyuanlin", R.drawable.suzhouyuanlin);
        mapImage.put("xuexiang", R.drawable.xuexiang);
        mapImage.put("l77", R.drawable.l77);
        mapImage.put("l99", R.drawable.l99);
        mapImage.put("beita", R.drawable.beita);
        mapImage.put("dachangtui", R.drawable.dachangtui);
        mapImage.put("dazhuang", R.drawable.dazhuang);
        mapImage.put("eric", R.drawable.eric);
        mapImage.put("huangshang", R.drawable.huangshang);
        mapImage.put("meishaonv", R.drawable.meishaonv);
        mapImage.put("shirley.png", R.drawable.shirley);
        mapImage.put("suke", R.drawable.suke);
        mapImage.put("tutu", R.drawable.tutu);
        mapImage.put("xiaoha", R.drawable.xiaoha);
    }
}
