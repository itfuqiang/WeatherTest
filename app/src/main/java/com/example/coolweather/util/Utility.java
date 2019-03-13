package com.example.coolweather.util;

import android.util.Log;

import com.example.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class Utility {
    public static boolean handProvinceResponse(String response){
        try {
            JSONArray allProvince = new JSONArray(response);
            for(int i=0;i<allProvince.length();i++){
                JSONObject provinceObject = allProvince.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                province.save();
                Log.e("TAG",province.toString());
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG","HttpUtil未获取数据");
        }
        return false;
    }
}
