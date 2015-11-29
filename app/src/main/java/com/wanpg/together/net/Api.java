package com.wanpg.together.net;

import android.content.Context;
import android.util.Log;

import com.wanpg.together.data.TravelItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangjinpeng on 15/11/26.
 */
public class Api {

    public static boolean isMainThread() {
        // TODO Auto-generated method stub

        return Thread.currentThread().getName().equals("main");
    }

    public static void callback(ResultListener listener, int status, Object info) {
        if (listener != null) listener.onResult(status, info);
    }

    public interface ResultListener {
        int SUCCESS = 1;
        /** 请求失败 */
        int FAILURE = 2;
        /** 请求错误，未返回 */
        int ERROR = 3;
        /** 参数不合法 */
        int INVALIDATE = 4;
        /** 无网络连接 */
        int NETWORK_UNAVAILABLE = 5;
        void onResult(int status, Object info);
    }

    public static void addTravel(final Context context, final HashMap<String, Object> params, final ResultListener listener){
        if(isMainThread()){
            new Thread(){
                @Override
                public void run() {
                    requestAddTravel(context, params, listener);
                }
            }.start();

        }else{
            requestAddTravel(context, params, listener);
        }
    }

    private static void requestAddTravel(Context context, HashMap<String, Object> params, ResultListener listener){

        String api = HttpRequest.getApi(HttpRequest.API_NEW_TRAVEL, params);
        Log.d("wanpg", "api==" + api);
        HttpResult httpResult = HttpRequest.get(api);
        if(httpResult.result==null){
            listener.onResult(ResultListener.FAILURE, null);
        }else{
            String result = new String(httpResult.result);
            Log.d("wanpg", "result==" + result);
            listener.onResult(ResultListener.SUCCESS, result);
        }
    }

    public static void getTravelList(final Context context, final String pos, final String uid, final String postid, final ResultListener listener){
        if(isMainThread()){
            new Thread(){
                @Override
                public void run() {
                    requestGetTravelList(context, pos, uid, postid, listener);
                }
            }.start();

        }else{
            requestGetTravelList(context, pos, uid, postid, listener);
        }
    }

    private static void requestGetTravelList(Context context, String pos, String uid, String postid, ResultListener listener){
        HashMap<String, Object> params = new HashMap<>();
        params.put("dest_location", pos);
        params.put("user_id", uid);
        params.put("id", postid);
        String api = HttpRequest.getApi(HttpRequest.API_TRAVEL_LIST, params);
        Log.d("wanpg", "api==" + api);
        HttpResult httpResult = HttpRequest.get(api);
        if(httpResult.result==null){
            listener.onResult(ResultListener.FAILURE, null);
        }else{
            String result = new String(httpResult.result);
            Log.d("wanpg", "result==" + result);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
                int size = jsonArray.length();
                ArrayList<TravelItem> list = new ArrayList<>();
                for(int i=0;i<size;i++){
                    JSONObject jo = jsonArray.optJSONObject(i);
                    if(jo!=null){
                        list.add(TravelItem.create(jo));
                    }
                }
                listener.onResult(ResultListener.SUCCESS, list);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listener.onResult(ResultListener.ERROR, result);
        }
    }


    public static void getTravelListAboutMe(Context context, final String uid, final ResultListener listener){
        new Thread(){
            @Override
            public void run() {
                HashMap<String, Object> params = new HashMap<>();
                params.put("user_id", uid);
                String api = HttpRequest.getApi(HttpRequest.API_GET_MY_TRAVEL, params);
                Log.d("wanpg", "api==" + api);
                HttpResult httpResult = HttpRequest.get(api);
                if(httpResult.result==null){
                    listener.onResult(ResultListener.FAILURE, null);
                }else{
                    String result = new String(httpResult.result);
                    Log.d("wanpg", "result==" + result);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(result);
                        int size = jsonArray.length();
                        ArrayList<TravelItem> list = new ArrayList<>();
                        for(int i=0;i<size;i++){
                            JSONObject jo = jsonArray.optJSONObject(i);
                            if(jo!=null){
                                list.add(TravelItem.create(jo));
                            }
                        }
                        listener.onResult(ResultListener.SUCCESS, list);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    listener.onResult(ResultListener.ERROR, null);
                }
            }
        }.start();

    }


    public static void getUserInfo(final Context context, final String phone, final String id, final ResultListener listener){
        if(isMainThread()){
            new Thread(){
                @Override
                public void run() {
                    requestGetUserInfo(context, phone, id, listener);
                }
            }.start();

        }else{
            requestGetUserInfo(context, phone, id, listener);
        }
    }

    private static void requestGetUserInfo(Context context, String phone, String id, ResultListener listener){
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", id);
        params.put("phone", phone);

        String api = HttpRequest.getApi(HttpRequest.API_USER_INFO, params);
        Log.d("wanpg", "api==" + api);
        HttpResult httpResult = HttpRequest.get(api);
        if(httpResult.result==null){
            listener.onResult(ResultListener.FAILURE, null);
        }else{
            String result = new String(httpResult.result);
            Log.d("wanpg", "result==" + result);

            listener.onResult(ResultListener.SUCCESS, result);

        }
    }



    public static void joinTravel(Context context, final String user_id, final String post_id, final ResultListener listener){
        new Thread(){
            @Override
            public void run() {
                HashMap<String, Object> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("post_id", post_id);

                String api = HttpRequest.getApi(HttpRequest.API_JOIN_TRAVEL, params);
                Log.d("wanpg", "api==" + api);
                HttpResult httpResult = HttpRequest.get(api);
                if(httpResult.result==null){
                    listener.onResult(ResultListener.FAILURE, null);
                }else{
                    String result = new String(httpResult.result);
                    Log.d("wanpg", "result==" + result);
                    listener.onResult(ResultListener.SUCCESS, result);
                }
            }
        }.start();

    }

    public static void exitTravel(Context context, final String user_id, final String post_id, final ResultListener listener){
        new Thread(){
            @Override
            public void run() {
                HashMap<String, Object> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("post_id", post_id);

                String api = HttpRequest.getApi(HttpRequest.API_EXIT_TRAVEL, params);
                Log.d("wanpg", "api==" + api);
                HttpResult httpResult = HttpRequest.get(api);
                if(httpResult.result==null){
                    listener.onResult(ResultListener.FAILURE, null);
                }else{
                    String result = new String(httpResult.result);
                    Log.d("wanpg", "result==" + result);
                    listener.onResult(ResultListener.SUCCESS, result);
                }
            }
        }.start();

    }

    public static void getMessageList(Context context, final String user_id,  final ResultListener listener){
        new Thread(){
            @Override
            public void run() {
                HashMap<String, Object> params = new HashMap<>();
                params.put("user_id", user_id);

                String api = HttpRequest.getApi(HttpRequest.API_MESSAGE_LIST, params);
                Log.d("wanpg", "api==" + api);
                HttpResult httpResult = HttpRequest.get(api);
                if(httpResult.result==null){
                    listener.onResult(ResultListener.FAILURE, null);
                }else{
                    String result = new String(httpResult.result);
                    Log.d("wanpg", "result==" + result);
                    listener.onResult(ResultListener.SUCCESS, result);
                }
            }
        }.start();

    }

    public static void comment(Context context, final String user_id,  final String post_id, final String content, final ResultListener listener){
        new Thread(){
            @Override
            public void run() {
                HashMap<String, Object> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("content", content);
                params.put("post_id", post_id);

                String api = HttpRequest.getApi(HttpRequest.API_COMMENT, params);
                Log.d("wanpg", "api==" + api);
                HttpResult httpResult = HttpRequest.get(api);
                if(httpResult.result==null){
                    listener.onResult(ResultListener.FAILURE, null);
                }else{
                    String result = new String(httpResult.result);
                    Log.d("wanpg", "result==" + result);
                    listener.onResult(ResultListener.SUCCESS, result);
                }
            }
        }.start();

    }
}
