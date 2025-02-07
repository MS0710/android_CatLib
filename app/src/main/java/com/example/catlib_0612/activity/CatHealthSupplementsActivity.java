package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class CatHealthSupplementsActivity extends AppCompatActivity {
    private String TAG = "CatHealthSupplementsActivity";
    private GridView gv_catHealthSupplements_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"眼睛保健","心臟保健","消化健康","免疫系統","泌尿道系統保健","關節保健",
            "情緒穩定","皮膚保健",};
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_health_supplements);
        initView();
    }

    private void initView(){
        gv_catHealthSupplements_list = (GridView) findViewById(R.id.gv_catHealthSupplements_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catHealthSupplements_list.setAdapter(homeItemAdapter);
        gv_catHealthSupplements_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "眼睛保健":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_EyeHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Eye_Health");
                        intent.putExtra("Title", "保健食品-眼睛保健");
                        startActivity(intent);
                        break;
                    case "心臟保健":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_DigestiveHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Heart_Health");
                        intent.putExtra("Title", "保健食品-心臟保健");
                        startActivity(intent);
                        break;
                    case "消化健康":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_DigestiveHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Digestive_Health");
                        intent.putExtra("Title", "保健食品-消化健康");
                        startActivity(intent);
                        break;
                    case "免疫系統":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_ImmuneSystem");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Immune_System");
                        intent.putExtra("Title", "保健食品-免疫系統");
                        startActivity(intent);
                        break;
                    case "泌尿道系統保健":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_UrinaryTractHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Urinary_Tract_Health");
                        intent.putExtra("Title", "保健食品-泌尿道系統保健");
                        startActivity(intent);
                        break;
                    case "關節保健":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_JointHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Joint_Health");
                        intent.putExtra("Title", "保健食品-關節保健");
                        startActivity(intent);
                        break;
                    case "情緒穩定":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_EmotionalStability");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Emotional_Stability");
                        intent.putExtra("Title", "保健食品-情緒穩定");
                        startActivity(intent);
                        break;
                    case "皮膚保健":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv2", "_SkinHealth");
                        editor.apply();
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Health_Supplements_Skin_Health");
                        intent.putExtra("Title", "保健食品-皮膚保健");
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