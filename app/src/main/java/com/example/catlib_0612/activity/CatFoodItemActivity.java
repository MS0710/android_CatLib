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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class CatFoodItemActivity extends AppCompatActivity {
    private String TAG = "CatFoodItemActivity";
    private TextView txt_catFoodItem_title;
    private String titleTest;
    private GridView gv_catFoodItem_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] completeCatFood = {"一般","體重管理","泌尿道保健","皮膚照護","消化道管理","挑嘴貓","低敏",};
    private String[] completePrescriptionTitle = {"泌尿道配方","體重控制配方","胰臟配方","腸胃道配方","低敏配方","腫瘤營養照護","腎臟配方","血糖管理配方",};
    private String titleFlag;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private String catFoodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food_item);
        initView();
    }
    private void initView(){
        Log.d(TAG, "initView: ");
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        catFoodType = getPrefs.getString("catFoodType", "");
        Log.d(TAG, "onCreateView: catFoodType = "+catFoodType);

        titleFlag = getIntent().getStringExtra("Title");
        //Log.d(TAG, "initView: titleFlag = "+titleFlag);

        txt_catFoodItem_title = (TextView) findViewById(R.id.txt_catFoodItem_title);
        txt_catFoodItem_title.setText(titleFlag);

        gv_catFoodItem_list = (GridView) findViewById(R.id.gv_catFoodItem_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getApplicationContext(),list);
        gv_catFoodItem_list.setAdapter(homeItemAdapter);
        gv_catFoodItem_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                titleTest = titleFlag;
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "一般":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_General");
                        editor.apply();
                        titleTest = titleTest + "-一般";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "體重管理":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_WeightManagement");
                        editor.apply();
                        titleTest = titleTest + "-體重管理";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "泌尿道保健":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_UrinaryHealth");
                        editor.apply();
                        titleTest = titleTest + "-泌尿道保健";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "皮膚照護":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_SkinCare");
                        editor.apply();
                        titleTest = titleTest + "-皮膚照護";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "消化道管理":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_DigestiveCare");
                        editor.apply();
                        titleTest = titleTest + "-消化道管理";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "挑嘴貓":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_PickyEater");
                        editor.apply();
                        titleTest = titleTest + "-挑嘴貓";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "低敏":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_Hypoallergenic");
                        editor.apply();
                        titleTest = titleTest + "-低敏";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "泌尿道配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_UrinaryTractFormula");
                        editor.apply();
                        titleTest = titleTest + "-泌尿道配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "體重控制配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_WeightControlFormula");
                        editor.apply();
                        titleTest = titleTest + "-體重控制配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "胰臟配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_PancreaticFormula");
                        editor.apply();
                        titleTest = titleTest + "-胰臟配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "腸胃道配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_GastrointestinalFormula");
                        editor.apply();
                        titleTest = titleTest + "-腸胃道配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "低敏配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_HypoallergenicFormula");
                        editor.apply();
                        titleTest = titleTest + "-低敏配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "腫瘤營養照護":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_OncologyNutritionalCare");
                        editor.apply();
                        titleTest = titleTest + "-腫瘤營養照護";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "腎臟配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_RenalFormula");
                        editor.apply();
                        titleTest = titleTest + "-腎臟配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
                        startActivity(intent);
                        break;
                    case "血糖管理配方":
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv6", "_GlucoseManagementFormula");
                        editor.apply();
                        titleTest = titleTest + "-血糖管理配方";
                        intent = new Intent(getApplicationContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", titleTest);
                        intent.putExtra("Title", titleFlag);
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
        list.clear();
        if (catFoodType.equals("一般")){
            for (int i=0; i< completeCatFood.length; i++){
                list.add(new HomeItem(completeCatFood[i]));
            }
        }else {
            for (int i=0; i< completePrescriptionTitle.length; i++){
                list.add(new HomeItem(completePrescriptionTitle[i]));
            }
        }
    }
}