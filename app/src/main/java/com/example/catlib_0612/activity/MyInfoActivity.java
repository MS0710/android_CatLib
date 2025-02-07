package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.MainActivity;
import com.example.catlib_0612.R;
import com.example.catlib_0612.data.UserInfo;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MyInfoActivity extends AppCompatActivity {
    private String TAG = "MyInfoActivity";
    private int IMAGE_REQUEST_CODE = 1;
    private ImageView img_MyInfo_people,img_MyInfo_back;
    private EditText edit_MyInfo_account,edit_MyInfo_name;
    private TextView txt_MyInfo_tip;
    private Button btn_MyInfo_modify;
    private CardView cv_MyInfo_people;
    private String userName,userAccount,userPassword,userPicture,userID;
    private String account,name;
    private StorageReference storageReference,pic_storage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private int userCunt;
    private boolean flag_ok;
    private String[] accountBuf = new String[100];
    private String[] nameBuf = new String[100];
    private ContentResolver resolver;
    private Uri uri;
    private String picture,picturePath,data_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
    }

    private void initView(){
        resolver = this.getContentResolver();
        userCunt = 0;
        for (int i=0; i<100;i++){
            accountBuf[i] = "";
        }
        flag_ok = true;
        storageReference = FirebaseStorage.getInstance().getReference();

        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        userID = getPrefs.getString("userID", "");
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPassword = getPrefs.getString("userPassword", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserInfo");

        img_MyInfo_people = (ImageView) findViewById(R.id.img_MyInfo_people);
        cv_MyInfo_people = (CardView)findViewById(R.id.cv_MyInfo_people);
        img_MyInfo_back = (ImageView) findViewById(R.id.img_MyInfo_back);
        btn_MyInfo_modify = (Button) findViewById(R.id.btn_MyInfo_modify);
        edit_MyInfo_account  = (EditText) findViewById(R.id.edit_MyInfo_account);
        edit_MyInfo_name  = (EditText) findViewById(R.id.edit_MyInfo_name);
        txt_MyInfo_tip = (TextView) findViewById(R.id.txt_MyInfo_tip);
        edit_MyInfo_account.setText(userAccount);
        edit_MyInfo_name.setText(userName);
        loadUserPicture();

        img_MyInfo_back.setOnClickListener(onClick);
        btn_MyInfo_modify.setOnClickListener(onClick);
        cv_MyInfo_people.setOnClickListener(onClick);
    }

    @Override
    public void onStart() {
        super.onStart();
        //readExistingData();

        flag_ok = true;
        userCunt = 0;
        readExistingData();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.img_MyInfo_back){
                Log.d(TAG, "onClick: img_MyInfo_back");
                finish();
            }else if(view.getId() == R.id.btn_MyInfo_modify){
                Log.d(TAG, "onClick: btn_MyInfo_modify");
                account = edit_MyInfo_account.getText().toString();
                name = edit_MyInfo_name.getText().toString();
                if (checkAccount()){
                    txt_MyInfo_tip.setText("帳號已被使用");
                }else {
                    txt_MyInfo_tip.setText("");
                    modifyUser();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }else if(view.getId() == R.id.cv_MyInfo_people){
                Log.d(TAG, "onClick: btn_MyInfo_modify");
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        }
    };

    private void modifyUser(){
        Log.d(TAG, "addNewUser: ");
        uploadPicture(userID);
        UserInfo userInfo = new UserInfo(account, userPassword,name,userPicture);
        myRef.child(userID).setValue(userInfo);
        //flag_ok = false;
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
                    img_MyInfo_people.setImageURI(Uri.fromFile(file));
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

    private boolean checkAccount(){
        for (int i=0; i<userCunt ; i++){
            if (accountBuf[i].equals(account)){
                return true;
            }
        }
        return false;
    }


    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onDataChange: user001 ="+dataSnapshot.child("user001").child("account").getValue().toString());
                //Log.d(TAG, "onDataChange: user002 ="+dataSnapshot.child("user002").child("account").getValue().toString());
                if (flag_ok){
                    userCunt = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: "+snapshot.toString());
                        Log.d(TAG, "onDataChange: userCunt = " + userCunt);
                        accountBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("account").getValue().toString();
                        nameBuf[userCunt] = dataSnapshot.child("user"+userCunt).child("name").getValue().toString();
                        userCunt++;
                    }
                    Log.d(TAG, "onDataChange: ---------userCunt = " + userCunt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadPicture(String _picture){
        String timeStamp;

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
            picture = _picture+".png";
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                //Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                //uri = selectedImage;
                uri = data.getData();//取得相片路徑
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex); //获取照片路径
                Log.d(TAG, "onActivityResult: path = "+picturePath);
                cursor.close();
                //Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                //img_signUp_people.setImageBitmap(bitmap);
                //將該路徑的圖片轉成bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                //設定ImageView圖片
                img_MyInfo_people.setImageBitmap(bitmap);
                /////////
                ContentResolver contentResolver = getContentResolver();
                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                data_list = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
                Log.d(TAG, "onActivityResult: data_list = "+data_list);
                //////////
            } catch (Exception e) {
                // TODO Auto-generatedcatch block
                e.printStackTrace();
            }
        }
    }
}