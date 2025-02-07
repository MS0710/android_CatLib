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

public class CatBreedActivity extends AppCompatActivity {
    private String TAG = "CatBreedActivity";
    private GridView gv_catBreed_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"品種貓","米克斯",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_breed);
        initView();
    }

    private void initView(){
        gv_catBreed_list = (GridView) findViewById(R.id.gv_catBreed_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catBreed_list.setAdapter(homeItemAdapter);
        gv_catBreed_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "品種貓":
                        intent = new Intent(getApplicationContext(),PurebredCatActivity.class);
                        startActivity(intent);
                        break;
                    case "米克斯":
                        intent = new Intent(getApplicationContext(),MixCatActivity.class);
                        startActivity(intent);
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