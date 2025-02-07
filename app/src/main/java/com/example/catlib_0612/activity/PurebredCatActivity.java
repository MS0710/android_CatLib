package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.CatBreedAdapter;
import com.example.catlib_0612.adapter.CatPatternAdapter;
import com.example.catlib_0612.data.CatBreed;
import com.example.catlib_0612.data.CatPattern;

import java.util.ArrayList;
import java.util.List;

public class PurebredCatActivity extends AppCompatActivity {
    private String TAG = "PurebredCatActivity";
    private GridView gv_purebredCat_list;
    private List<CatBreed> list;
    private CatBreedAdapter catBreedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purebred_cat);
        initView();
    }

    private void initView(){
        gv_purebredCat_list = (GridView) findViewById(R.id.gv_purebredCat_list);
        setData();
        catBreedAdapter = new CatBreedAdapter(list,getApplicationContext());
        gv_purebredCat_list.setAdapter(catBreedAdapter);


    }
    private void setData(){
        list = new ArrayList<>();
        list.add(new CatBreed("波斯貓（Persian）",R.drawable.purebred_cat_1,getString(R.string.purebred_cat_1_1),getString(R.string.purebred_cat_1_2)));
        list.add(new CatBreed("緬因貓（Maine Coon）",R.drawable.purebred_cat_2,getString(R.string.purebred_cat_2_1),getString(R.string.purebred_cat_2_2)));
        list.add(new CatBreed("英國短毛貓(British Shorthair)",R.drawable.purebred_cat_3,getString(R.string.purebred_cat_3_1),getString(R.string.purebred_cat_3_2)));
        list.add(new CatBreed("美國短毛貓",R.drawable.purebred_cat_4,getString(R.string.purebred_cat_4_1),getString(R.string.purebred_cat_4_2)));
        list.add(new CatBreed("俄羅斯藍貓（Russian Blue）",R.drawable.purebred_cat_5,getString(R.string.purebred_cat_5_1),getString(R.string.purebred_cat_5_2)));
        list.add(new CatBreed("孟加拉貓（Bengal）",R.drawable.purebred_cat_6,getString(R.string.purebred_cat_6_1),getString(R.string.purebred_cat_6_2)));
        list.add(new CatBreed("阿比西尼亞貓（Abyssinian）",R.drawable.purebred_cat_7,getString(R.string.purebred_cat_7_1),getString(R.string.purebred_cat_7_2)));
        list.add(new CatBreed("蘇格蘭摺耳貓（Scottish Fold）",R.drawable.purebred_cat_8,getString(R.string.purebred_cat_8_1),getString(R.string.purebred_cat_8_2)));
        list.add(new CatBreed("挪威森林貓（Norwegian Forest Cat）",R.drawable.purebred_cat_9,getString(R.string.purebred_cat_9_1),getString(R.string.purebred_cat_9_2)));
        //list.add(new CatBreed("無毛貓(學名)",R.drawable.purebred_cat_2,getString(R.string.purebred_cat_2_1),getString(R.string.purebred_cat_2_2)));
        //list.add(new CatBreed("暹羅貓",R.drawable.purebred_cat_3,getString(R.string.purebred_cat_2_1),getString(R.string.purebred_cat_2_2)));
        //緬甸貓
        //瑪恩島貓（Manx）
    }

}