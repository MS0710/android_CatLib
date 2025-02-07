package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.Feed;
import com.example.catlib_0612.data.UserMyCat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class CatFoodContentActivity extends AppCompatActivity {
    private String TAG = "CatFoodContentActivity";
    private TextView txt_catFoodContent_title;
    private ImageView img_feedContent_back,img_feedContent_heart,img_feedContent_picture;
    private TextView txt_feedContent_brand,txt_feedContent_name,txt_feedContent_introduce,txt_feedContent_element;
    private String titleFlag;
    private String curTag;
    private String picture;
    private String brand;
    private String name;
    private String introduce;
    private String element;
    private StorageReference storageReference,pic_storage;
    private File fileTemp;
    private boolean heartFlag;
    private SharedPreferences getPrefs;
    private String userName,userAccount,userPicture;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefHistory;
    private String userFireBaseKey;
    private int userCunt;
    private int userHistoryCunt;
    ////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food_content);
        initView();
    }

    private void initView(){
        Log.d(TAG, "initView: ");
        heartFlag = false;
        userFireBaseKey = "";
        userCunt = 0;
        userHistoryCunt = 1;
        storageReference = FirebaseStorage.getInstance().getReference();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);
        userAccount = userAccount.replace(".", "");
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserMyFavourite");

        myRefHistory = database.getReference("UserHistory");

        titleFlag = getIntent().getStringExtra("Title");
        curTag = getIntent().getStringExtra("tag");
        picture = getIntent().getStringExtra("picture");
        brand = getIntent().getStringExtra("brand");
        name = getIntent().getStringExtra("name");
        introduce = getIntent().getStringExtra("introduce");
        element = getIntent().getStringExtra("element");
        Log.d(TAG, "initView: curTag = "+curTag);

        txt_catFoodContent_title = (TextView) findViewById(R.id.txt_catFoodContent_title);
        txt_catFoodContent_title.setText(titleFlag);

        img_feedContent_back = (ImageView) findViewById(R.id.img_feedContent_back);
        img_feedContent_heart = (ImageView) findViewById(R.id.img_feedContent_heart);
        img_feedContent_picture = (ImageView) findViewById(R.id.img_feedContent_picture);
        txt_feedContent_brand = (TextView) findViewById(R.id.txt_feedContent_brand);
        txt_feedContent_name = (TextView) findViewById(R.id.txt_feedContent_name);
        txt_feedContent_introduce = (TextView) findViewById(R.id.txt_feedContent_introduce);
        txt_feedContent_element = (TextView) findViewById(R.id.txt_feedContent_element);

        img_feedContent_back.setOnClickListener(onClick);
        img_feedContent_heart.setOnClickListener(onClick);

        readPicture(picture);
        //img_feedContent_picture.setImageDrawable(getDrawable(picture));
        txt_feedContent_brand.setText(brand);
        txt_feedContent_name.setText(name);
        txt_feedContent_introduce.setText(introduce);
        txt_feedContent_element.setText(element);

        //addNewHistoryItem();
        readExistingHistoryData();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.img_feedContent_back){
                Log.d(TAG, "onClick: img_feedContent_back");
                finish();
            }else if (view.getId() == R.id.img_feedContent_heart){
                Log.d(TAG, "onClick: img_feedContent_heart");
                if (heartFlag){
                    img_feedContent_heart.setImageDrawable(getDrawable(R.drawable.heart_grey));
                    removeItem();
                    heartFlag = false;
                }else {
                    img_feedContent_heart.setImageDrawable(getDrawable(R.drawable.heart_red));
                    addNewItem();
                    heartFlag = true;
                }
            }
        }
    };

    private void readPicture(String picture){
        pic_storage = storageReference.child(picture);
        //final File file;
        try {
            fileTemp = File.createTempFile("images","png");
            pic_storage.getFile(fileTemp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    img_feedContent_picture.setImageURI(Uri.fromFile(fileTemp));
                    Log.d(TAG, "onSuccess: ");
                    //dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addNewItem(){
        Log.d(TAG, "addNewItem: ");

        Feed feed= new Feed(curTag,picture,brand,name,introduce,element);
        myRef.child(userAccount).child("userMyFavourite"+userCunt).setValue(feed);
    }

    private void addNewHistoryItem(){
        Log.d(TAG, "addNewHistoryItem: ");
        Log.d(TAG, "addNewHistoryItem: userHistoryCunt = "+userHistoryCunt);
        Log.d(TAG, "addNewHistoryItem: brand = "+brand);
        Log.d(TAG, "addNewHistoryItem: name = "+name);

        Feed feed= new Feed(curTag,picture,brand,name,introduce,element);
        myRefHistory.child(userAccount).child("userHistory"+userHistoryCunt).setValue(feed);
    }

    private void removeItem(){
        myRef.child(userAccount).child(userFireBaseKey).removeValue();
    }

    @Override
    public void onStart() {
        super.onStart();
        heartFlag = false;
        userCunt = 0;
        readExistingData();
    }

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String _curTAG,_brand,_name;
                for (DataSnapshot postSnapshot : dataSnapshot.child(userAccount).getChildren()){
                    //Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                    _curTAG = postSnapshot.child("tag").getValue().toString();
                    _brand = postSnapshot.child("brand").getValue().toString();
                    _name = postSnapshot.child("name").getValue().toString();
                    if (curTag.equals(_curTAG) && _brand.equals(brand) && _name.equals(name)){
                        userFireBaseKey = postSnapshot.getKey().toString();
                        Log.d(TAG, "onDataChange: key = "+userFireBaseKey);
                        img_feedContent_heart.setImageDrawable(getDrawable(R.drawable.heart_red));
                        heartFlag = true;
                        Log.d(TAG, "onDataChange: heartFlag = "+heartFlag);
                    }
                    userCunt++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readExistingHistoryData(){
        Log.d(TAG, "readExistingHistoryData: ");
        myRefHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String _curTAG = null,_picture = null,_brand = null,_name = null,_introduce = null,_element = null;
                boolean tempFlag = false;

                for (DataSnapshot postSnapshot : dataSnapshot.child(userAccount).getChildren()){
                    //Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                    _curTAG = postSnapshot.child("tag").getValue().toString();
                    _picture = postSnapshot.child("picture").getValue().toString();
                    _brand = postSnapshot.child("brand").getValue().toString();
                    _name = postSnapshot.child("name").getValue().toString();
                    _introduce = postSnapshot.child("introduce").getValue().toString();
                    _element = postSnapshot.child("element").getValue().toString();
                    if (curTag.equals(_curTAG) && _brand.equals(brand) && _name.equals(name)){
                        tempFlag = true;
                    }
                    userHistoryCunt++;
                }
                if (!tempFlag){
                    Log.d(TAG, "onDataChange: userHistoryCunt = "+userHistoryCunt);
                    addNewHistoryItem();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}