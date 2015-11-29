package com.wanpg.together.data;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class PosModel {
    /*id*/
    public int id;
    /*父id*/
    public int parentId;
    /*名字*/
    public String name;

    public static PosModel create(int id, int pid, String name){
        PosModel pm = new PosModel();
        pm.id = id;
        pm.parentId = pid;
        pm.name = name;
        return pm;
    }
}
