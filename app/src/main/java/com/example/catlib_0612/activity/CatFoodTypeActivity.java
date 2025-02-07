package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class CatFoodTypeActivity extends AppCompatActivity {
    private String TAG = "CatFoodTypeActivity";
    private TextView txt_catFoodType_title;
    private String titleTest;
    private GridView gv_catFoodType_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"一般飼料","處方飼料",};
    private String titleFlag;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food_type);
        initView();
    }

    private void initView(){
        Log.d(TAG, "initView: ");
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        getPrefs.getString("catFoodType", "");

        titleFlag = getIntent().getStringExtra("Title");

        txt_catFoodType_title = (TextView) findViewById(R.id.txt_catFoodType_title);
        txt_catFoodType_title.setText(titleFlag);

        gv_catFoodType_list = (GridView) findViewById(R.id.gv_catFoodType_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catFoodType_list.setAdapter(homeItemAdapter);
        gv_catFoodType_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                titleTest = titleFlag;
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "一般飼料":
                        titleTest = titleTest + "-一般";
                        intent = new Intent(getApplicationContext(), CatFoodGrainActivity.class);
                        editor = getPrefs.edit();
                        editor.putString("catFoodType", "一般");
                        editor.putString("Feed_lv4", "_NonPrescription");
                        editor.apply();
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "處方飼料":
                        titleTest = titleTest + "-處方";
                        intent = new Intent(getApplicationContext(), CatFoodGrainActivity.class);
                        editor = getPrefs.edit();
                        editor.putString("catFoodType", "處方");
                        editor.putString("Feed_lv4", "_Prescription");
                        editor.apply();
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