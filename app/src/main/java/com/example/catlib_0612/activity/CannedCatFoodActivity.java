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

public class CannedCatFoodActivity extends AppCompatActivity {
    private String TAG = "CannedCatFoodActivity";
    private TextView txt_cannedCatFood_title;
    private String titleTest;
    private GridView gv_cannedCatFood_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"主食罐頭","副食罐頭",};
    private String titleFlag;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canned_cat_food);
        initView();
    }

    private void initView(){
        Log.d(TAG, "initView: ");
        titleFlag = getIntent().getStringExtra("Title");

        txt_cannedCatFood_title = (TextView) findViewById(R.id.txt_cannedCatFood_title);
        txt_cannedCatFood_title.setText(titleFlag);

        gv_cannedCatFood_list = (GridView) findViewById(R.id.gv_cannedCatFood_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_cannedCatFood_list.setAdapter(homeItemAdapter);
        gv_cannedCatFood_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                titleTest = titleFlag;
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "主食罐頭":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_CompleteCatFood");
                        editor.apply();
                        titleTest = "主食罐";
                        intent = new Intent(getApplicationContext(), DryFeedActivity.class);
                        intent.putExtra("Title", titleTest);
                        startActivity(intent);
                        break;
                    case "副食罐頭":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_ComplementaryCatFood");
                        editor.apply();
                        titleTest = "副食罐";
                        intent = new Intent(getApplicationContext(), DryFeedActivity.class);
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