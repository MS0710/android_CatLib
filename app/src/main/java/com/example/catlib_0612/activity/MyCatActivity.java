package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.AnimalHouseAdapter;
import com.example.catlib_0612.adapter.MyCatAdapter;
import com.example.catlib_0612.data.AninalHouse;
import com.example.catlib_0612.data.Feed;
import com.example.catlib_0612.data.UserMyCat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCatActivity extends AppCompatActivity {
    private String TAG = "MyCatActivity";
    private ImageView img_myCat_add;
    private GridView gv_myCat_list;
    private MyCatAdapter myCatAdapter;
    private List<UserMyCat> list;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefBirthday;
    private SharedPreferences getPrefs;
    private String userName,userAccount,userPicture;
    private String catName, catBreed,catBirthDay,catPicture,catKey,catFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cat);
        initView();
    }
    private void initView(){
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);
        userAccount = userAccount.replace(".", "");
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserMyCat");
        myRefBirthday = database.getReference("UserMyCatBirthday");

        img_myCat_add = (ImageView) findViewById(R.id.img_myCat_add);
        img_myCat_add.setOnClickListener(onClick);

        gv_myCat_list = (GridView) findViewById(R.id.gv_myCat_list);
        list = new ArrayList<>();
        /*list.add(new UserMyCat("111","222","333","444"));
        list.add(new UserMyCat("111","222","333","444"));
        list.add(new UserMyCat("111","222","333","444"));
        list.add(new UserMyCat("111","222","333","444"));*/
        //setData();

        myCatAdapter = new MyCatAdapter(getApplicationContext(),list);
        gv_myCat_list.setAdapter(myCatAdapter);
        gv_myCat_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemLongClick: position = "+position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyCatActivity.this);
                alertDialog.setTitle("設定貓咪生日通知");
                alertDialog.setMessage("設定此貓咪生日通知(只能選定一隻)");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: 確定");
                        catName = list.get(position).getName();
                        catBreed = list.get(position).getBreed();
                        catBirthDay = list.get(position).getBirthday();
                        catPicture = list.get(position).getPicture();
                        catKey = list.get(position).getKey();
                        catFlag = list.get(position).getFlag();
                        addMyCatBirthday(catName, catBreed,catBirthDay,catPicture,catKey,catFlag);
                    }
                });
                alertDialog.setNegativeButton("刪除此貓",(dialog, which) -> {
                    Log.d(TAG, "onClick: 刪除此貓");
                    myRef.child(userAccount).child(list.get(position).getKey()).removeValue();
                });
                alertDialog.setNeutralButton("取消",(dialog, which) -> {
                    Log.d(TAG, "onClick: 取消");
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                return false;
            }
        });
    }
    
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.img_myCat_add){
                Log.d(TAG, "onClick: img_myCat_add");
                Intent intent = new Intent(getApplicationContext(), AddMyCatActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        readExistingData();
    }

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name;
                String breed;
                String birthday;
                String picture;
                String key,flag;

                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.child(userAccount).getChildren()){
                    Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                    key = postSnapshot.getKey().toString();
                    name = postSnapshot.child("name").getValue().toString();
                    breed = postSnapshot.child("breed").getValue().toString();
                    birthday = postSnapshot.child("birthday").getValue().toString();
                    picture = postSnapshot.child("picture").getValue().toString();
                    flag = postSnapshot.child("flag").getValue().toString();
                    Log.d(TAG, "onDataChange: name = "+name);
                    Log.d(TAG, "onDataChange: breed = "+breed);
                    Log.d(TAG, "onDataChange: birthday = "+birthday);
                    Log.d(TAG, "onDataChange: picture = "+picture);
                    list.add(new UserMyCat(name,breed,birthday,picture,key,flag));

                }
                myCatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addMyCatBirthday(String _catName,String  _catBreed,String _birthDay,String _picture,String _key,String _flag){
        Log.d(TAG, "addMyCatBirthday: ");
        String catName1,catBreed1,catBirthDay1,catPicture1,catKey1,catFlag1;
        for (int i=0; i<list.size();i++){
            catName1 = list.get(i).getName();
            catBreed1 = list.get(i).getBreed();
            catBirthDay1 = list.get(i).getBirthday();
            catPicture1 = list.get(i).getPicture();
            catKey1 = list.get(i).getKey();
            UserMyCat userMyCat = new UserMyCat(catName1, catBreed1,catBirthDay1,catPicture1,catKey1,"false");
            myRef.child(userAccount).child(catKey1).setValue(userMyCat);
        }
        UserMyCat userMyCat = new UserMyCat(_catName, _catBreed,_birthDay,_picture,_key,"true");
        myRef.child(userAccount).child(_key).setValue(userMyCat);
    }
}