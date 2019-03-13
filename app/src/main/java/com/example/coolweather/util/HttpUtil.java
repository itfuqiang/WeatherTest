package com.example.coolweather.util;

import android.util.Log;

import com.example.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class HttpUtil  {
    public static void sendOkhttpRequest(String adress,okhttp3.Callback callback){
        //得到实例
        OkHttpClient client = new OkHttpClient();
        //准备数据
        Request request = new Request.Builder().url("http://guolin.tech/api/china").build();
        //链接并返回callback
        client.newCall(request).enqueue(callback);
        Log.e("TAG","sendOkhttpRequest");
    }

}
