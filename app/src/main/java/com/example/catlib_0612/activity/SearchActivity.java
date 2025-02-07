package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {
    private String TAG = "SearchActivity";
    private String searchItem;
    private EditText edit_search_input;
    private ImageView img_search_search;
    private GridView gv_search_list;
    private List<Feed> list;
    private FeedAdapter feedAdapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }
    
    private void initView(){
        Log.d(TAG, "initView: ");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("FeedData");

        searchItem = getIntent().getStringExtra("searchItem");
        readExistingData();

        edit_search_input = (EditText) findViewById(R.id.edit_search_input);
        edit_search_input.setText(searchItem);
        img_search_search = (ImageView) findViewById(R.id.img_search_search);
        img_search_search.setOnClickListener(onClick);

        gv_search_list = (GridView) findViewById(R.id.gv_search_list);
        list = new ArrayList<>();
        feedAdapter = new FeedAdapter(getApplicationContext(),list);
        gv_search_list.setAdapter(feedAdapter);
        gv_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CatFoodContentActivity.class);
                //intent.putExtra("Title", titleFlag);
                intent.putExtra("Title", "搜尋");
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
    
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.img_search_search){
                Log.d(TAG, "onClick: img_search_search");
                searchItem = edit_search_input.getText().toString();
                if(searchItem.equals("")){
                    Toast.makeText(getApplicationContext(),"請輸入搜尋",Toast.LENGTH_SHORT).show();
                }else {
                    readExistingData();
                }
            }
        }
    };

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
                    name = postSnapshot.child("name").getValue().toString();
                    brand = postSnapshot.child("brand").getValue().toString();
                    if (name.contains(searchItem) || brand.contains(searchItem)){
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