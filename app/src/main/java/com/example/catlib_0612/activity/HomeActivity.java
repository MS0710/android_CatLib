package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.LongRunningService;
import com.example.catlib_0612.MainActivity;
import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;
import com.example.catlib_0612.data.UserMyCat;
import com.example.catlib_0612.fragment.HomeFragment;
import com.example.catlib_0612.fragment.MainFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = "HomeActivity";
    /////
    private DrawerLayout drawer;
    private TextView txt_navHeader_name,txt_navHeader_mail;
    private ImageView img_navHeader_picture;
    private String userName,userAccount,userPicture;
    private StorageReference storageReference,pic_storage;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    private void initView(){
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
        myRef = database.getReference("UserMyCat");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //增加一个抽屉开关
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //将活动注册为导航视图的一个监听器
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        txt_navHeader_name = (TextView) header.findViewById(R.id.txt_navHeader_name);
        txt_navHeader_mail = (TextView) header.findViewById(R.id.txt_navHeader_mail);
        img_navHeader_picture = (ImageView) header.findViewById(R.id.img_navHeader_picture);

        //txt_navHeader_name.setText("123456789");
        //txt_navHeader_mail.setText("123456789@1213");
        txt_navHeader_name.setText(userName);
        txt_navHeader_mail.setText(getPrefs.getString("userAccount", ""));
        //img_navHeader_picture.setImageDrawable(getDrawable(R.drawable.foot));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;

        if (id == R.id.nav_userInfo){
            intent = new Intent(this, MyInfoActivity.class);
        } else if (id == R.id.nav_suggestion){
            intent = new Intent(this, UserFeedBackActivity.class);
        } else if (id == R.id.nav_myCat){
            intent = new Intent(this, MyCatActivity.class);
        }

            //else if (id == R.id.nav_sent)fragment = new HomeFragment();
            //else if (id == R.id.nav_trash)fragment = new HomeFragment();
            //else if (id == R.id.nav_help)intent = new Intent(this, HelpActivity.class);
            //else if (id == R.id.nav_feedback)intent = new Intent(this, FeedbackActivity.class);
            //else fragment = new HomeFragment();
        else fragment = new MainFragment();

        //根据用户在抽屉中选择的选项，显示相应的片段和活动
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }else startActivity(intent);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //用户单击某一项时关闭抽屉
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //后退时，关闭抽屉
    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        readExistingData();
        loadUserPicture();
    }

    private void loadUserPicture(){
        pic_storage = storageReference.child(userPicture);
        final File file;
        try {
            file = File.createTempFile("images","png");
            pic_storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: file = "+file);
                    img_navHeader_picture.setImageURI(Uri.fromFile(file));
                    Log.d(TAG, "onSuccess: ");
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

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name;
                String breed;
                String birthday;
                String flag;

                for (DataSnapshot postSnapshot : dataSnapshot.child(userAccount).getChildren()){
                    Log.d(TAG, "onDataChange: "+postSnapshot.toString());
                    name = postSnapshot.child("name").getValue().toString();
                    breed = postSnapshot.child("breed").getValue().toString();
                    birthday = postSnapshot.child("birthday").getValue().toString();
                    flag = postSnapshot.child("flag").getValue().toString();
                    if (flag.equals("true")){
                        Log.d(TAG, "onDataChange: name = "+name);
                        Log.d(TAG, "onDataChange: breed = "+breed);
                        Log.d(TAG, "onDataChange: birthday = "+birthday);
                        String[] tokens = birthday.split("/");
                        Log.d(TAG, "initView: tokens = "+tokens[0]);
                        Log.d(TAG, "initView: tokens = "+tokens[1]);//month
                        Log.d(TAG, "initView: tokens = "+tokens[2]);//day
                        int month = Integer.parseInt(tokens[1]);
                        if ((month-1)<0){
                            month = 0;
                        }else {
                            month = month-1;
                        }
                        Intent intent = new Intent(HomeActivity.this, LongRunningService.class);
                        editor = getPrefs.edit();
                        editor.putString("catName", name);
                        editor.apply();
                        //intent.putExtra("name", name);
                        intent.putExtra("month", String.valueOf(month));
                        intent.putExtra("day", tokens[2]);
                        //开启关闭Service
                        startService(intent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}