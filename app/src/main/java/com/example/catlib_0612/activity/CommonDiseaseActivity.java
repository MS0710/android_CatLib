package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class CommonDiseaseActivity extends AppCompatActivity {
    private String TAG = "CommonDiseaseActivity";
    private GridView gv_commonDisease_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"常見疾病","傳染性疾病","體內寄生蟲","體外寄生蟲",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_disease);
        initView();
    }
    private void initView(){
        gv_commonDisease_list = (GridView) findViewById(R.id.gv_commonDisease_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_commonDisease_list.setAdapter(homeItemAdapter);
        gv_commonDisease_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "常見疾病":
                        intent = new Intent(getApplicationContext(), GeneralDiseasesActivity.class);
                        startActivity(intent);
                        break;
                    case "傳染性疾病":
                        intent = new Intent(getApplicationContext(), InfectiousDiseasesActivity.class);
                        startActivity(intent);
                        break;
                    case "體內寄生蟲":
                        intent = new Intent(getApplicationContext(), EntozoonActivity.class);
                        startActivity(intent);
                        break;
                    case "體外寄生蟲":
                        intent = new Intent(getApplicationContext(), EctoparasiticideActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                        break;
                }
                //Intent intent = new Intent(getApplicationContext(), AnimalHouseInfoActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void setData(){
        for (int i=0; i< title.length; i++){
            list.add(new HomeItem(title[i]));
        }
    }
}