package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.CatBodyAdapter;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.CatBody;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class CatBodyActivity extends AppCompatActivity {
    private String TAG = "CatBodyActivity";
    private String title;
    private TextView txt_CatBody_title;
    private GridView gv_CatBody_list;
    private CatBodyAdapter catBodyAdapter;
    private List<CatBody> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_body);
        initView();
    }

    private void initView(){
        title = getIntent().getStringExtra("title");
        Log.d(TAG, "initView: title = "+title);
        txt_CatBody_title = (TextView)findViewById(R.id.txt_CatBody_title);
        txt_CatBody_title.setText(title);
        gv_CatBody_list = (GridView) findViewById(R.id.gv_CatBody_list);
        list = new ArrayList<>();

        setData();
        catBodyAdapter = new CatBodyAdapter(list,getApplicationContext());
        gv_CatBody_list.setAdapter(catBodyAdapter);

    }

    private void setData(){
        list.clear();
        if(title.equals("耳朵")){
            list.add(new CatBody("耳朵向前",getString(R.string.cat_body_ear_1)));
            list.add(new CatBody("耳朵直立且向外旋轉",getString(R.string.cat_body_ear_2)));
            list.add(new CatBody("耳朵貼平",getString(R.string.cat_body_ear_3)));
            list.add(new CatBody("耳朵向側",getString(R.string.cat_body_ear_4)));
            list.add(new CatBody("一耳前一耳後",getString(R.string.cat_body_ear_5)));
        }else if(title.equals("尾巴")){
            list.add(new CatBody("尾巴豎直挺立",getString(R.string.cat_body_tail_1)));
            list.add(new CatBody("尾巴輕輕搖晃",getString(R.string.cat_body_tail_2)));
            list.add(new CatBody("尾巴膨脹",getString(R.string.cat_body_tail_3)));
            list.add(new CatBody("尾巴夾緊",getString(R.string.cat_body_tail_4)));
            list.add(new CatBody("尾巴輕輕擺動",getString(R.string.cat_body_tail_5)));
            list.add(new CatBody("尾巴慢慢擺動",getString(R.string.cat_body_tail_6)));
            list.add(new CatBody("尾巴成問號形",getString(R.string.cat_body_tail_7)));
        }else if(title.equals("身體")){
            list.add(new CatBody("腹部朝上躺著",getString(R.string.cat_body_body_1)));
            list.add(new CatBody("身體低伏、耳朵貼後",getString(R.string.cat_body_body_2)));
            list.add(new CatBody("背部拱起、毛發蓬鬆",getString(R.string.cat_body_body_3)));
            list.add(new CatBody("側躺但警惕",getString(R.string.cat_body_body_4)));
            list.add(new CatBody("四肢緊繃，身體呈C型",getString(R.string.cat_body_body_5)));
            list.add(new CatBody("身體緊貼地面，準備撲擊",getString(R.string.cat_body_body_6)));
            list.add(new CatBody("緩慢移動或靠近",getString(R.string.cat_body_body_7)));
            list.add(new CatBody("躺平並伸展",getString(R.string.cat_body_body_8)));
        }else if(title.equals("叫聲")){
            list.add(new CatBody("喵喵叫",getString(R.string.cat_body_meow_1)));
            list.add(new CatBody("咕嚕咕嚕聲",getString(R.string.cat_body_meow_2)));
            list.add(new CatBody("尖叫或怒吼",getString(R.string.cat_body_meow_3)));
            list.add(new CatBody("嘶嘶聲和吼叫",getString(R.string.cat_body_meow_4)));
            list.add(new CatBody("喚鳴或鳴叫",getString(R.string.cat_body_meow_5)));
            list.add(new CatBody("貓鳴歌唱",getString(R.string.cat_body_meow_6)));
            list.add(new CatBody("突然尖叫",getString(R.string.cat_body_meow_7)));
        }else if(title.equals("眼神")){
            list.add(new CatBody("緩慢眨眼",getString(R.string.cat_body_eye_1)));
            list.add(new CatBody("凝視",getString(R.string.cat_body_eye_2)));
            list.add(new CatBody("瞳孔放大",getString(R.string.cat_body_eye_3)));
            list.add(new CatBody("眼睛微縮",getString(R.string.cat_body_eye_4)));
            list.add(new CatBody("閉眼",getString(R.string.cat_body_eye_5)));
            list.add(new CatBody("眼睛半開",getString(R.string.cat_body_eye_6)));

        }

    }
}