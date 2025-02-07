package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
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

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.FeedAdapter;
import com.example.catlib_0612.data.Feed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatFoodItemInfoActivity extends AppCompatActivity {
    private String TAG = "CatFoodItemInfoActivity";
    private TextView txt_catFoodItemInfo_title;
    private GridView gv_catFoodItemInfo_list;
    private List<Feed> list;
    private FeedAdapter feedAdapter;
    private String titleFlag,TitleTag,nowTag;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SharedPreferences getPrefs;
    private String Feed_lv1,Feed_lv2,Feed_lv3,Feed_lv4,Feed_lv5,Feed_lv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food_item_info);
        initView();
    }
    private void initView(){
        Log.d(TAG, "initView: ");
        titleFlag = getIntent().getStringExtra("Title");
        TitleTag = getIntent().getStringExtra("TitleTag");
        Log.d(TAG, "initView: titleFlag = "+titleFlag);
        Log.d(TAG, "initView: TitleTag = "+TitleTag);

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Feed_lv1 = getPrefs.getString("Feed_lv1", "");
        Feed_lv2 = getPrefs.getString("Feed_lv2", "");
        Feed_lv3 = getPrefs.getString("Feed_lv3", "");
        Feed_lv4 = getPrefs.getString("Feed_lv4", "");
        Feed_lv5 = getPrefs.getString("Feed_lv5", "");
        Feed_lv6 = getPrefs.getString("Feed_lv6", "");
        Log.d(TAG, "initView: Feed_lv1 = "+Feed_lv1);
        Log.d(TAG, "initView: Feed_lv2 = "+Feed_lv2);
        Log.d(TAG, "initView: Feed_lv3 = "+Feed_lv3);
        Log.d(TAG, "initView: Feed_lv4 = "+Feed_lv4);
        Log.d(TAG, "initView: Feed_lv5 = "+Feed_lv5);
        Log.d(TAG, "initView: Feed_lv6 = "+Feed_lv6);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("FeedData");

        txt_catFoodItemInfo_title = (TextView) findViewById(R.id.txt_catFoodItemInfo_title);
        txt_catFoodItemInfo_title.setText(titleFlag);

        gv_catFoodItemInfo_list = (GridView) findViewById(R.id.gv_catFoodItemInfo_list);
        list = new ArrayList<>();

        feedAdapter = new FeedAdapter(getApplicationContext(),list);
        gv_catFoodItemInfo_list.setAdapter(feedAdapter);
        gv_catFoodItemInfo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CatFoodContentActivity.class);
                intent.putExtra("Title", titleFlag);
                intent.putExtra("tag", list.get(i).getTAG());
                intent.putExtra("picture", list.get(i).getPicture());
                intent.putExtra("brand", list.get(i).getBrand());
                intent.putExtra("name", list.get(i).getName());
                intent.putExtra("introduce", list.get(i).getIntroduce());
                intent.putExtra("element", list.get(i).getElement());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        nowTag = Feed_lv1+Feed_lv2+Feed_lv3+Feed_lv4+Feed_lv5+Feed_lv6;
        Log.d(TAG, "onStart: nowTag = "+nowTag);
        readExistingData();
    }

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String picture,title,tag1,tag2,tag3,note,price,pattern,meters,floor,type,introduce,key,uploader;
                //String picture2,picture3;

                String curTAG,picture,brand,name,introduce,element;

                Feed feed = null;
                list.clear();
                //title = dataSnapshot.child("house"+1).child("title").getValue().toString();
                //Log.d(TAG, "onDataChange: title = "+title);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                    curTAG = postSnapshot.child("tag").getValue().toString();
                    if (nowTag.equals(curTAG)){
                        Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                        curTAG = postSnapshot.child("tag").getValue().toString();
                        picture = postSnapshot.child("picture").getValue().toString();
                        brand = postSnapshot.child("brand").getValue().toString();
                        name = postSnapshot.child("name").getValue().toString();
                        introduce = postSnapshot.child("introduce").getValue().toString();
                        element = postSnapshot.child("element").getValue().toString();

                        Log.d(TAG, "onDataChange: curTAG = "+curTAG);
                        Log.d(TAG, "onDataChange: picture = "+picture);
                        Log.d(TAG, "onDataChange: brand = "+brand);
                        Log.d(TAG, "onDataChange: name = "+name);
                        Log.d(TAG, "onDataChange: introduce = "+introduce);
                        Log.d(TAG, "onDataChange: element = "+element);

                        feed = new Feed(curTAG,picture,brand,name,introduce,element);
                        list.add(feed);
                    }
                }
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}