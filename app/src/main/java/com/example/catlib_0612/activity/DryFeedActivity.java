package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class DryFeedActivity extends AppCompatActivity {
    private String TAG = "DryFeedActivity";
    private GridView gv_dryFeed_list;
    private TextView txt_dryFeed_title;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"幼母貓","成貓","老貓", "全貓",};
    private String titleTest;
    private String titleFlag;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dry_feed);
        initView();
    }

    private void initView(){
        titleFlag = getIntent().getStringExtra("Title");

        txt_dryFeed_title = (TextView) findViewById(R.id.txt_dryFeed_title);
        txt_dryFeed_title.setText(titleFlag);

        gv_dryFeed_list = (GridView) findViewById(R.id.gv_dryFeed_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_dryFeed_list.setAdapter(homeItemAdapter);
        gv_dryFeed_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                titleTest = titleFlag;
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "幼母貓":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv3", "_FemaleKitten");
                        editor.apply();
                        titleTest = titleTest + "-幼母貓";
                        intent = new Intent(getApplicationContext(), CatFoodTypeActivity.class);
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "成貓":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv3", "_AdultCat");
                        editor.apply();
                        titleTest = titleTest + "-成貓";
                        intent = new Intent(getApplicationContext(), CatFoodTypeActivity.class);
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "老貓":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv3", "_SeniorCat");
                        editor.apply();
                        titleTest = titleTest + "-老貓";
                        intent = new Intent(getApplicationContext(), CatFoodTypeActivity.class);
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "全貓":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv3", "_AllStages");
                        editor.apply();
                        titleTest = titleTest + "-全貓";
                        intent = new Intent(getApplicationContext(), CatFoodTypeActivity.class);
                        intent.putExtra("Title", titleTest);
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