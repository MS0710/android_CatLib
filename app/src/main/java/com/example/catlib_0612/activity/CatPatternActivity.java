package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.CatPatternAdapter;
import com.example.catlib_0612.data.CatPattern;

import java.util.ArrayList;
import java.util.List;

public class CatPatternActivity extends AppCompatActivity {
    private String TAG = "CatPatternActivity";
    private GridView gv_catPattern_list;
    private List<CatPattern> list;
    private CatPatternAdapter catPatternAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_pattern);
        initView();
    }

    private void initView(){
        gv_catPattern_list = (GridView) findViewById(R.id.gv_catPattern_list);
        setData();
        catPatternAdapter = new CatPatternAdapter(list,getApplicationContext());
        gv_catPattern_list.setAdapter(catPatternAdapter);

    }

    private void setData(){
        list = new ArrayList<>();
        list.add(new CatPattern("【黑貓－最懂你的心】",getString(R.string.animal_pattern_1),R.drawable.pattern_1));
        list.add(new CatPattern("【白貓－優雅貴族】",getString(R.string.animal_pattern_2),R.drawable.pattern_2));
        list.add(new CatPattern("【橘貓－巨型吞食獸】",getString(R.string.animal_pattern_3),R.drawable.pattern_3));
        list.add(new CatPattern("【玳瑁－鄰家乖乖女】",getString(R.string.animal_pattern_4),R.drawable.pattern_4));
        list.add(new CatPattern("【賓士－貓界二哈】",getString(R.string.animal_pattern_5),R.drawable.pattern_5));
        list.add(new CatPattern("【虎斑－悶騷反差萌】",getString(R.string.animal_pattern_6),R.drawable.pattern_6));
        list.add(new CatPattern("【三花－傲嬌小公主】",getString(R.string.animal_pattern_7),R.drawable.pattern_7));
    }
}