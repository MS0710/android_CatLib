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

public class CatFoodGrainActivity extends AppCompatActivity {
    private String TAG = "CatFoodGrainActivity";
    private TextView txt_catFoodGrain_title;
    private String titleTest;
    private GridView gv_catFoodGrain_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"無穀","非無穀",};
    private String titleFlag;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food_grain);
        initView();
    }
    private void initView(){
        Log.d(TAG, "initView: ");
        titleFlag = getIntent().getStringExtra("Title");

        txt_catFoodGrain_title = (TextView) findViewById(R.id.txt_catFoodGrain_title);
        txt_catFoodGrain_title.setText(titleFlag);

        gv_catFoodGrain_list = (GridView) findViewById(R.id.gv_catFoodGrain_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catFoodGrain_list.setAdapter(homeItemAdapter);
        gv_catFoodGrain_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                titleTest = titleFlag;
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "無穀":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv5", "_GrainFree");
                        editor.apply();
                        titleTest = titleTest + "-無穀";
                        intent = new Intent(getApplicationContext(), CatFoodItemActivity.class);
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "非無穀":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv5", "_NotGrainFree");
                        editor.apply();
                        titleTest = titleTest + "-非無穀";
                        intent = new Intent(getApplicationContext(), CatFoodItemActivity.class);
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