package com.example.catlib_0612;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.activity.AnimalHouseInfoActivity;
import com.example.catlib_0612.activity.ForgetPasswordActivity;
import com.example.catlib_0612.activity.HomeActivity;
import com.example.catlib_0612.activity.SignUpActivity;
import com.example.catlib_0612.data.Feed;
import com.example.catlib_0612.data.UserInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Button btn_main_signUp,btn_main_signIn;
    private TextInputLayout textInput_main_account_layout,textInput_main_password_layout;
    private TextView txt_main_forgetPassword;
    private EditText edit_main_account,edit_main_password;
    private ImageView img_main_watchPassword;
    private String account,password;
    private int userCunt;
    private int correctCunt;
    private String[] IDBuf = new String[100];
    private String[] accountBuf = new String[100];
    private String[] passwordBuf = new String[100];
    private String[] nameBuf = new String[100];
    private String[] pictureBuf = new String[100];
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private boolean flag_password;
    //////////
    private DatabaseReference myRefData;

    private String CHANNEL_ID = "Coder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 沒有權限在此重新申請權限
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.POST_NOTIFICATIONS,
            }, 100);
        }else{
            // 有權限了
            Log.d(TAG, "onCreate: 有權限了");
        }
        initView();
        initView2();
    }

    private void initView(){
        Log.d(TAG, "initView: ");
        /**檢查手機版本是否支援通知；若支援則新增"頻道"*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "DemoCode", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }

        flag_password = false;
        account = "";
        password = "";
        userCunt = 0;
        correctCunt = 0;

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        getPrefs.getString("userID", "");
        getPrefs.getString("userName", "");
        account = getPrefs.getString("userAccount", "");
        password = getPrefs.getString("userPassword", "");
        getPrefs.getString("userPicture", "");
        getPrefs.getString("catName", "");

        //storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserInfo");

        edit_main_account = (EditText)findViewById(R.id.edit_main_account);
        edit_main_password = (EditText)findViewById(R.id.edit_main_password);
        textInput_main_account_layout = (TextInputLayout) findViewById(R.id.textInput_main_account_layout);
        textInput_main_password_layout = (TextInputLayout) findViewById(R.id.textInput_main_password_layout);
        img_main_watchPassword = (ImageView)findViewById(R.id.img_main_watchPassword);
        txt_main_forgetPassword = (TextView) findViewById(R.id.txt_main_forgetPassword);

        btn_main_signUp = (Button) findViewById(R.id.btn_main_signUp);
        btn_main_signIn = (Button) findViewById(R.id.btn_main_signIn);
        btn_main_signUp.setOnClickListener(onClick);
        btn_main_signIn.setOnClickListener(onClick);
        img_main_watchPassword.setOnClickListener(onClick);
        txt_main_forgetPassword.setOnClickListener(onClick);


        //edit_main_account.setText("abc7@test.com");
        //edit_main_password.setText("123");

        edit_main_account.setText(account);
        edit_main_password.setText(password);
        //edit_main_account.setText("cherryqq525@gmail.com");
        //edit_main_password.setText("cherry544000");

    }

    @Override
    protected void onStart() {
        super.onStart();
        readExistingData();
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_main_signUp){
                Log.d(TAG, "onClick: btn_main_signUp");
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }else if(view.getId() == R.id.btn_main_signIn){
                Log.d(TAG, "onClick: btn_main_signIn");
                account = edit_main_account.getText().toString();
                password = edit_main_password.getText().toString();
                if(checkEditViewEmpty()){
                    if(checkAccount() && checkPassword()){
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        editor = getPrefs.edit();
                        editor.putString("userID", IDBuf[correctCunt]);
                        editor.putString("userName", nameBuf[correctCunt]);
                        editor.putString("userAccount", accountBuf[correctCunt]);
                        editor.putString("userPassword", passwordBuf[correctCunt]);
                        editor.putString("userPicture", pictureBuf[correctCunt]);
                        editor.apply();
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),"帳號密碼錯誤",Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(view.getId() == R.id.img_main_watchPassword){
                Log.d(TAG, "onClick: img_main_watchPassword");
                if(flag_password){
                    edit_main_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag_password = false;
                }else {
                    edit_main_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag_password = true;
                }
            }else if(view.getId() == R.id.txt_main_forgetPassword){
                Log.d(TAG, "onClick: txt_main_forgetPassword");
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
            }

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onRequestPermissionsResult: YES");
        }else {
            Log.d(TAG, "onRequestPermissionsResult: NO");
            Toast.makeText(MainActivity.this,"請開啟地圖權限",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAccount(){
        for (int i=0; i<userCunt ; i++){
            if(account.equals(accountBuf[i])){
                correctCunt = i;
                return true;
            }
        }
        return false;
    }

    private boolean checkPassword(){
        for (int i=0; i<userCunt ; i++){
            if(password.equals(passwordBuf[i])){
                return true;
            }
        }
        return false;
    }

    private boolean checkEditViewEmpty(){
        if(TextUtils.isEmpty(account)){
            textInput_main_account_layout.setError("請輸入帳號");
            textInput_main_password_layout.setError("");
            return false;
        }else if(TextUtils.isEmpty(password)){
            textInput_main_account_layout.setError("");
            textInput_main_password_layout.setError("請輸入密碼");
            return false;
        }else {
            textInput_main_account_layout.setError("");
            textInput_main_password_layout.setError("");
            return true;
        }
    }

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userCunt = 0;
                //Log.d(TAG, "onDataChange: user001 ="+dataSnapshot.child("user001").child("account").getValue().toString());
                //Log.d(TAG, "onDataChange: user002 ="+dataSnapshot.child("user002").child("account").getValue().toString());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot.toString());
                    Log.d(TAG, "onDataChange: userCunt = " + userCunt);
                    IDBuf[userCunt] = "user"+userCunt;
                    accountBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("account").getValue().toString();
                    passwordBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("password").getValue().toString();
                    nameBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("name").getValue().toString();
                    pictureBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("picture").getValue().toString();
                    userCunt++;
                }
                Log.d(TAG, "onDataChange: ---------userCunt = " + userCunt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////
    private StorageReference storageReference,pic_storage;
    private String picture;
    private void initView2(){
        storageReference = FirebaseStorage.getInstance().getReference();
        myRefData = database.getReference("FeedData");
        Button btn_main_addData = (Button) findViewById(R.id.btn_main_addData);
        btn_main_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
            }
        });
    }

    private void addNewItem(){
        Log.d(TAG, "addNewUser: ");
        int feedItemCunt = 0;
        int j;

        String itemTAG;
        int[] itemList_picture = new int[30];
        List<Feed> feeds = new ArrayList<>();
        String brand,name,introduce,element;
        String curItemTag;

        ////////////////////////////////////////////////////////////////
        //itemTAG = "D_FK_NP_G_G";//乾飼料-幼母貓-一般飼料-無穀-一般
        itemTAG = "DryCat_FemaleKitten_NonPrescription_GrainFree_General";//乾飼料-幼母貓-一般飼料-無穀-一般
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_1;
        curItemTag = itemTAG+j;
        Log.d(TAG, "addNewItem: itemTAG = "+itemTAG);
        Log.d(TAG, "addNewItem: curItemTag = "+curItemTag);
        brand = "Instinct 原點";
        name = "雞肉無穀幼貓配方";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_1_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_2;
        curItemTag = itemTAG+j;
        brand = "歐睿健Orijen";
        name = "鮮雞無榖配方幼貓飼料";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_2_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_2_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_3;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "無穀高蛋白幼貓飼料";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_3_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_3_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_4;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "GC-1 幼貓/懷孕貓-雞肉石榴";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_4_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_4_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_5;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "OC-0 幼貓/懷孕貓-無穀鱈魚南瓜";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_5_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_5_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.kitten_female_cat_general_no_grain_6;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "SC-1 螺旋藻貓糧-幼貓鯡魚";
        introduce = getString(R.string.kitten_female_cat_general_no_grain_6_1);
        element = getString(R.string.kitten_female_cat_general_no_grain_6_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //itemTAG = "D_FK_NP_NG_G";//乾飼料-幼母貓-一般飼料-非無穀-一般
        itemTAG = "DryCat_FemaleKitten_NonPrescription_NotGrainFree_General";//乾飼料-幼母貓-一般飼料-非無穀-一般
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_female_cat_general_grain_1;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "第一盛宴低GI配方幼母貓飼料 ";
        introduce = getString(R.string.kitten_female_cat_general_grain_1_1);
        element = getString(R.string.kitten_female_cat_general_grain_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //itemTAG = "D_O_NP_G_G";//乾飼料-老貓-一般-無穀-一般
        itemTAG = "DryCat_SeniorCat_NonPrescription_GrainFree_General";//乾飼料-老貓-一般-無穀-一般
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_old_cat_general_no_grain_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-6 室內貓體態泌尿保健-鴨肉蘆筍";
        introduce = getString(R.string.kitten_old_cat_general_no_grain_1_1);
        element = getString(R.string.kitten_old_cat_general_no_grain_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //itemTAG = "D_adult_prescription_NG_liver";//乾飼料-成貓-處方-非無穀-肝臟
        itemTAG = "DryCat_AdultCat_Prescription_NotGrainFree_liver";//乾飼料-成貓-處方-非無穀-肝臟
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_adult_cat_prescription_no_grain_liver_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "VC-2 VET LIFE貓用肝臟配方";
        introduce = getString(R.string.kitten_adult_cat_prescription_no_grain_liver_1_1);
        element = getString(R.string.kitten_adult_cat_prescription_no_grain_liver_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //itemTAG = "D_adult_prescription_NG_kidney";//乾飼料-成貓-處方-非無穀-腎臟
        itemTAG = "DryCat_AdultCat_Prescription_NotGrainFree_RenalFormula";//乾飼料-成貓-處方-非無穀-腎臟
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_adult_cat_prescription_no_grain_kidney_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "VC-5 VET LIFE貓用腎臟配方";
        introduce = getString(R.string.kitten_adult_cat_prescription_no_grain_kidney_1_1);
        element = getString(R.string.kitten_adult_cat_prescription_no_grain_kidney_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //itemTAG = "D_adult_prescription_NG_intestine";//乾飼料-成貓-處方-非無穀-腸胃道
        itemTAG = "DryCat_AdultCat_Prescription_NotGrainFree_GastrointestinalFormula";//乾飼料-成貓-處方-非無穀-腸胃道
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.kitten_adult_cat_prescription_no_grain_intestine_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "VC-1 VET LIFE貓用腸胃道配方";
        introduce = getString(R.string.kitten_adult_cat_prescription_no_grain_intestine_1_1);
        element = getString(R.string.kitten_adult_cat_prescription_no_grain_intestine_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AllStages_NonPrescription_GrainFree_General";//乾飼料-全貓-一般-無穀-一般功能
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_1;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "鮭魚無穀全貓配方";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_1_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_2;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "鴨肉無穀全貓配方";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_2_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_2_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_3;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "雞肉無穀全貓配方";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_3_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_3_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_4;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "鴨肉凍乾全貓配方";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_4_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_4_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_5;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "雞肉凍乾全貓配方";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_5_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_5_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_6;
        curItemTag = itemTAG+j;
        brand = "歐睿健Orijen";
        name = "六種鮮魚無榖貓飼料";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_6_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_6_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_7;
        curItemTag = itemTAG+j;
        brand = "歐睿健Orijen";
        name = "鮮雞愛貓無榖貓飼料";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_7_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_7_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_8;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "農場盛宴無穀配方貓飼料";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_8_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_8_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_allstages_nonprescription_grainfree_general_9;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "草原盛宴無穀配方貓飼料";
        introduce = getString(R.string.dry_allstages_nonprescription_grainfree_general_9_1);
        element = getString(R.string.dry_allstages_nonprescription_grainfree_general_9_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AllStages_NonPrescription_NotGrainFree_General";//乾飼料-全貓-一般-非無穀-一般功能
        j = 0;
        feeds.clear();
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_General";//乾飼料-成貓-一般-無穀-一般功能
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_1;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "皇極鮮鴨成貓配方";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_2;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "皇極鮮雞成貓配方";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_2_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_2_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_3;
        curItemTag = itemTAG+j;
        brand = "歐睿健Orijen";
        name = "愛貓守護8無榖配方貓飼料";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_3_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_3_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_4;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "GC-2 全齡貓-雞肉石榴";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_4_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_4_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_5;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "GC-5 全齡貓-野豬蘋果";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_5_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_5_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_6;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "GC-3 全齡貓-羊肉藍莓";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_6_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_6_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_7;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "OC-1 全齡貓-無穀鯡魚甜橙";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_7_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_7_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_8;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "OC-2 全齡貓-無榖鯡魚南瓜";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_8_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_8_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_9;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "OC-3 全齡貓-低穀鱈魚甜橙";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_9_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_9_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_10;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "PC-1 全齡貓-鵪鶉石榴";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_10_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_10_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_11;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "PC-2 全齡貓-鹿肉蘋果";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_11_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_11_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_12;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "PC-4 全齡貓-鴨肉哈密瓜";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_12_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_12_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_13;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "SC-3 螺旋藻貓糧-全齡貓鯡魚";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_13_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_13_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_general_14;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "SC-2 螺旋藻貓糧-全齡貓羊肉";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_general_14_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_general_14_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_WeightManagement";//乾飼料-成貓-一般-無穀-減脂/體重管理
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_weightmanagement_1;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "健康減重低脂雞肉凍乾成貓配方";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_weightmanagement_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_weightmanagement_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_weightmanagement_2;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-4 體重管理-羊肉蘆筍";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_weightmanagement_2_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_weightmanagement_2_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_UrinaryHealth";//乾飼料-成貓-一般-無穀-泌尿道保健
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_urinaryhealth_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-5 泌尿道紓壓保健-鴨肉蔓越莓";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_urinaryhealth_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_urinaryhealth_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_SkinCare";//乾飼料-成貓-一般-無穀-皮毛保健
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_skincare_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-2 皮毛保健-鯡魚椰子";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_skincare_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_skincare_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_skincare_2;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-3 皮毛保健-鵪鶉椰子";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_skincare_2_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_skincare_2_1);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_DigestiveCare";//乾飼料-成貓-一般-無穀-消化道管理
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_digestivecare_1;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "QC-1 腸胃道保健-羊肉茴香";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_digestivecare_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_digestivecare_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_PickyEater";//乾飼料-成貓-一般-無穀-挑嘴貓
        j = 0;
        feeds.clear();
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_GrainFree_Hypoallergenic";//乾飼料-成貓-一般-無穀-低敏
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_grainfree_hypoallergenic_1;
        curItemTag = itemTAG+j;
        brand = "Instinct 原點";
        name = "火雞肉低敏成貓配方";
        introduce = getString(R.string.dry_adultcat_nonprescription_grainfree_hypoallergenic_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_grainfree_hypoallergenic_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_NotGrainFree_General";//乾飼料-成貓-一般-非無穀-一般功能
        j = 0;
        feeds.clear();
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_notgrainfree_general_1;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "田園收穫低GI配方貓飼料 ";
        introduce = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_1_1);
        element = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_1_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_notgrainfree_general_2;
        curItemTag = itemTAG+j;
        brand = "愛肯拿 ACANA";
        name = "豐盛漁獲低GI配方貓飼料";
        introduce = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_2_1);
        element = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_2_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_notgrainfree_general_3;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "LC-1 全齡貓-雞肉石榴";
        introduce = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_3_1);
        element = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_3_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_notgrainfree_general_4;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "LC-2 全齡貓-羊肉藍莓";
        introduce = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_4_1);
        element = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_4_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        j++;
        itemList_picture[j] = R.drawable.dry_adultcat_nonprescription_notgrainfree_general_5;
        curItemTag = itemTAG+j;
        brand = "Farmina 法米納";
        name = "FTC1-天然熱帶水果系列-全齡貓-雞肉芒果";
        introduce = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_5_1);
        element = getString(R.string.dry_adultcat_nonprescription_notgrainfree_general_5_2);
        feeds.add(new Feed(itemTAG,curItemTag+".png",brand,name,introduce,element));

        Log.d(TAG, "addNewItem: feeds.size() = "+feeds.size());
        feedItemCunt = uploadData(feedItemCunt,itemList_picture,feeds);
        ///////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_AdultCat_NonPrescription_NotGrainFree_General";//乾飼料-成貓-一般-非無穀-一般功能
        j = 0;
        feeds.clear();
        ///////////////////////////////////////////////////////////////////////////////////////////
        itemTAG = "DryCat_FemaleKitten_NonPrescription_NotGrainFree_General";//乾飼料-幼母貓-一般-無穀-一般功能
        j = 0;
        feeds.clear();

    }

    private int uploadData(int _feedItemCunt,int[] _itemList_picture,List<Feed> _feeds){
        String brand,name,introduce,element,pictureString;
        String curItemTag;

        for (int i=0; i<_feeds.size();i++){
            Log.d(TAG, "addNewItem: i = "+i);
            int picture = _itemList_picture[i];
            curItemTag = _feeds.get(i).getTAG();
            pictureString = _feeds.get(i).getPicture();
            brand = _feeds.get(i).getBrand();
            name = _feeds.get(i).getName();
            introduce = _feeds.get(i).getIntroduce();
            element = _feeds.get(i).getElement();
            Log.d(TAG, "addNewItem: picture = "+picture);
            Log.d(TAG, "addNewItem: brand = "+brand);
            Log.d(TAG, "addNewItem: name = "+name);
            Log.d(TAG, "addNewItem: introduce = "+introduce);
            Log.d(TAG, "addNewItem: element = "+element);

            uploadPicture(pictureString,picture);
            Feed feed = new Feed(curItemTag,pictureString,brand, name,introduce,element);
            myRefData.child("feed"+_feedItemCunt).setValue(feed);
            _feedItemCunt++;
        }

        return _feedItemCunt;
    }

    private void uploadPicture(String curTag,@AnyRes int drawableId){
        String timeStamp;
        Uri uri = getUriFromResId(MainActivity.this,drawableId);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        Log.d(TAG, "Calendar获取当前日期"+year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second);

        timeStamp = ""+year+month+day+hour+minute+second;

        if(uri != null){
            //picture = curTag+".png";
            picture = curTag;
            pic_storage = storageReference.child(picture);
            pic_storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"上傳PASS",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            picture= "people.png";
        }
    }

    public Uri getUriFromResId(@NonNull Context context, @AnyRes int drawableId) {
        return Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + context.getResources().getResourcePackageName(drawableId)
                        + '/' + context.getResources().getResourceTypeName(drawableId)
                        + '/' + context.getResources().getResourceEntryName(drawableId)
        );
    }


}