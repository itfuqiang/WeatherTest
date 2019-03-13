package com.example.coolweather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.db.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class ChooseareaFragment extends Fragment {
    private Button backButton;
    private TextView tieleText;
    private ListView listView;
    private ProgressDialog progressDialog;
    private List<String> dataList=new ArrayList<>();
    private ArrayAdapter<String> adapter;


     private List<Province> provinceList;
    //当前选中级别
    private int currentLevel;
    //选中的省
    private Province selectProvince;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //的到当前布局
        View view = inflater.inflate(R.layout.choose_area, container, false);

        //实例化
        backButton= (Button) view.findViewById(R.id.button_back);
        tieleText= (TextView) view.findViewById(R.id.title_text);
        listView= (ListView) view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvince();
        Log.e("TAG","onActivityCreated");
    }

    private void queryProvince(){
        tieleText.setText("China");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        //遍历
        if(provinceList.size()>0){
            provinceList.clear();
            for(Province province:provinceList){
                dataList.add(province.getProvinceName());
            }//更新页面
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        }
        else {
            String adress="http://guolin.tech/api/china";
            querFromService(adress,"province");

        }
    }
    //从服务器获取数据

            private void querFromService(String adress, final String type) {
                showProgressDialog();
                HttpUtil.sendOkhttpRequest(adress, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.e("TAG",responseText);
                boolean result=false;
                if("province".equals(type)){
                   result= Utility.handProvinceResponse(responseText);
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvince();
                            }
                        }
                    });
                }
            }
        });
    }
    //加载进度
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载....");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }//关闭进度
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
