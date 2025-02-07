package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.CatBreedAdapter;
import com.example.catlib_0612.adapter.CatMixAdapter;
import com.example.catlib_0612.data.CatBreed;
import com.example.catlib_0612.data.CatMix;

import java.util.ArrayList;
import java.util.List;

public class MixCatActivity extends AppCompatActivity {
    private String TAG = "PurebredCatActivity";
    private GridView gv_MixCat_list;
    private List<CatMix> list;
    private CatMixAdapter catMixAdapter;
    //gv_MixCat_list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_cat);
        initView();
    }

    private void initView(){
        gv_MixCat_list = (GridView) findViewById(R.id.gv_MixCat_list);
        setData();
        catMixAdapter = new CatMixAdapter(list,getApplicationContext());
        gv_MixCat_list.setAdapter(catMixAdapter);

    }

    private void setData(){
        list = new ArrayList<>();
        list.add(new CatMix("橘貓",R.drawable.cat_mix_1,"全身70～80％以上的橘色",getString(R.string.mix_cat_1)));
        list.add(new CatMix("賓士貓",R.drawable.cat_mix_2,"黑白雙色組成",getString(R.string.mix_cat_2)));
        list.add(new CatMix("白貓",R.drawable.cat_mix_3,"全身純白",getString(R.string.mix_cat_3)));
        list.add(new CatMix("黑貓",R.drawable.cat_mix_4,"全身純黑",getString(R.string.mix_cat_4)));
        list.add(new CatMix("玳瑁貓",R.drawable.cat_mix_5,"由深咖啡色、黑色、白色、橘色等多種花色混雜",getString(R.string.mix_cat_5)));
        list.add(new CatMix("三花貓",R.drawable.cat_mix_6,"全身覆有白、黑、橘3種毛色",getString(R.string.mix_cat_6)));
        list.add(new CatMix("虎斑貓",R.drawable.cat_mix_7,"多為咖啡色、橘色、黑色、白色交織成的條紋狀毛色",getString(R.string.mix_cat_7)));
    }
}