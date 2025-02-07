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

public class CatPersonalityActivity extends AppCompatActivity {
    private String TAG = "CatPersonalityActivity";
    private GridView gv_catPersonality_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"花紋毛色","貓咪肉掌","耳朵","尾巴","身體","叫聲","睡覺姿勢與位置","眼神",
            "嘔吐",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_personality);
        initView();
    }

    private void initView(){
        gv_catPersonality_list = (GridView) findViewById(R.id.gv_catPersonality_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catPersonality_list.setAdapter(homeItemAdapter);
        gv_catPersonality_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "花紋毛色":
                        intent = new Intent(getApplicationContext(), CatPatternActivity.class);
                        startActivity(intent);
                        break;
                    case "貓咪肉掌":
                        intent = new Intent(getApplicationContext(), CatPawActivity.class);
                        startActivity(intent);
                        break;
                    case "耳朵":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","耳朵");
                        startActivity(intent);
                        break;
                    case "尾巴":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","尾巴");
                        startActivity(intent);
                        break;
                    case "身體":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","身體");
                        startActivity(intent);
                        break;
                    case "叫聲":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","叫聲");
                        startActivity(intent);
                        break;
                    case "睡覺姿勢與位置":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","睡覺姿勢與位置");
                        startActivity(intent);
                        break;
                    case "眼神":
                        intent = new Intent(getApplicationContext(), CatBodyActivity.class);
                        intent.putExtra("title","眼神");
                        startActivity(intent);
                        break;
                    case "嘔吐":
                        intent = new Intent(getApplicationContext(), CatVomitingActivity.class);
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