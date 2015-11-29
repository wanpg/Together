package com.wanpg.together.net;

public class HttpResult {

    //状态类型定义
    public static final int NETWORK_UNAVAILABLE = -1;
    public static final int PARAM_INVALIDATE = -2;
    public static final int SUCCESS = 200;

    public byte[] result;
    public int status = SUCCESS;


    public void setResult(byte[] result) {
        this.result = result;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
