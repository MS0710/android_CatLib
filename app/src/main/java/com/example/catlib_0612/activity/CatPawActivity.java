package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.CatPawAdapter;
import com.example.catlib_0612.data.CatPaw;

import java.util.ArrayList;
import java.util.List;

public class CatPawActivity extends AppCompatActivity {
    private String TAG = "CatPawActivity";
    private GridView gv_catPaw_list;
    private List<CatPaw> list;
    private CatPawAdapter catPawAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_paw);
        initView();
    }
    private void initView(){
        gv_catPaw_list = (GridView) findViewById(R.id.gv_catPaw_list);
        setData();
        catPawAdapter = new CatPawAdapter(list,getApplicationContext());
        gv_catPaw_list.setAdapter(catPawAdapter);

    }

    private void setData(){
        list = new ArrayList<>();
        list.add(new CatPaw("貓咪肉掌功能","",getString(R.string.animal_catPaw_1),R.drawable.catpaw_1));
        list.add(new CatPaw("A型貓掌 - 肉球頂端像愛心","A型貓咪是親人的撒嬌鬼！",getString(R.string.animal_catPaw_2),R.drawable.catpaw_2));
        list.add(new CatPaw("B型貓掌 - 肉球頂端圓","B型的貓咪是愛冒險的人氣王！",getString(R.string.animal_catPaw_3),R.drawable.catpaw_3));
        list.add(new CatPaw("C型貓掌 - 肉球頂端平、兩邊微微隆起","C型貓咪是優雅、孤傲的氣質小生。",getString(R.string.animal_catPaw_4),R.drawable.catpaw_4));
        list.add(new CatPaw("D型 貓掌 - 肉球類似C型、頂端溝較淺","D型貓咪是難搞王！",getString(R.string.animal_catPaw_5),R.drawable.catpaw_5));
        list.add(new CatPaw("E型 貓掌 - 肉球整體像三角型","E型貓咪是恰北北！",getString(R.string.animal_catPaw_6),R.drawable.catpaw_6));


    }
}