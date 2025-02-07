package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.UserInfo;
import com.example.catlib_0612.data.UserMyCat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMyCatActivity extends AppCompatActivity {
    private String TAG = "AddMyCatActivity";
    private Button btn_addMyCat_send;
    private TextView txt_addMyCat_birthday,txt_addMyCat_msgError;
    private EditText edit_addMyCat_breed,edit_addMyCat_name;
    private CardView cv_addMyCat_picture;
    private ImageView img_addMyCat_picture;
    private int IMAGE_REQUEST_CODE = 1;
    private Uri uri;
    private ContentResolver resolver;
    private String picturePath,data_list,picture;
    private String birthDay;
    private DatePickerDialog.OnDateSetListener datePicker;
    private Calendar calendar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference storageReference,pic_storage;
    private SharedPreferences getPrefs;
    private String userName,userAccount,userPicture;
    private int userCunt;
    private String catName,catBreed;
    private ProgressDialog dialog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_cat);
        initView();
    }

    private void initView(){
        birthDay = "";
        userCunt = 0;
        catName = "";
        catBreed = "";
        resolver = this.getContentResolver();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);
        userAccount = userAccount.replace(".", "");
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);

        storageReference = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserMyCat");

        btn_addMyCat_send = (Button) findViewById(R.id.btn_addMyCat_send);
        txt_addMyCat_birthday = (TextView) findViewById(R.id.txt_addMyCat_birthday);
        edit_addMyCat_breed = (EditText) findViewById(R.id.edit_addMyCat_breed);
        edit_addMyCat_name = (EditText) findViewById(R.id.edit_addMyCat_name);
        cv_addMyCat_picture = (CardView) findViewById(R.id.cv_addMyCat_picture);
        img_addMyCat_picture = (ImageView) findViewById(R.id.img_addMyCat_picture);
        txt_addMyCat_msgError = (TextView) findViewById(R.id.txt_addMyCat_msgError);
        cv_addMyCat_picture.setOnClickListener(onClick);
        txt_addMyCat_birthday.setOnClickListener(onClick);
        btn_addMyCat_send.setOnClickListener(onClick);

        calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                birthDay = "" + sdf.format(calendar.getTime());
                txt_addMyCat_birthday.setText(birthDay);
            }
        };

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH)+1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d(TAG, "initView: "+mDay+"-"+mMonth+"-"+mYear);
        birthDay = ""+mYear+"/"+mMonth+"/"+mDay;
        txt_addMyCat_birthday.setText(birthDay);

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.cv_addMyCat_picture){
                Log.d(TAG, "onClick: cv_addMyCat_picture");
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

            }else if(view.getId() == R.id.txt_addMyCat_birthday){
                Log.d(TAG, "onClick: txt_addMyCat_birthday");
                DatePickerDialog dialog = new DatePickerDialog(AddMyCatActivity.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }else if(view.getId() == R.id.btn_addMyCat_send){
                Log.d(TAG, "onClick: btn_addMyCat_send");
                catName = edit_addMyCat_name.getText().toString();
                catBreed = edit_addMyCat_breed.getText().toString();
                if (catName.equals("") || catBreed.equals("")){
                    txt_addMyCat_msgError.setText("請填寫完整資訊");
                }else {
                    dialog = new ProgressDialog(AddMyCatActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setTitle("上傳圖片資料中");
                    dialog.show();
                    addNewCat();
                }
            }

        }
    };

    private void addNewCat(){
        Log.d(TAG, "addNewUser: ");
        String timeStamp;

        Calendar calendar1 = Calendar.getInstance();
        int year = calendar1.get(Calendar.YEAR);
        int month = calendar1.get(Calendar.MONTH)+1;
        int day = calendar1.get(Calendar.DAY_OF_MONTH);
        int hour = calendar1.get(Calendar.HOUR_OF_DAY);
        int minute = calendar1.get(Calendar.MINUTE);
        int second = calendar1.get(Calendar.SECOND);
        Log.d(TAG, "Calendar获取当前日期"+year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second);

        timeStamp = "_"+year+month+day+hour+minute+second;

        uploadPicture();
        UserMyCat userMyCat = new UserMyCat(catName, catBreed,birthDay,picture,"userMyCat"+userCunt,"false");
        myRef.child(userAccount).child("userMyCat"+userCunt+timeStamp).setValue(userMyCat);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        readExistingData();
    }
    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onDataChange: user001 ="+dataSnapshot.child("user001").child("account").getValue().toString());
                //Log.d(TAG, "onDataChange: user002 ="+dataSnapshot.child("user002").child("account").getValue().toString());
                userCunt = 0;
                for (DataSnapshot snapshot : dataSnapshot.child(userAccount).getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot.toString());
                    userCunt++;
                }
                Log.d(TAG, "onDataChange: userCunt = " + userCunt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadPicture(){

        if(uri != null){
            picture = userName+"_"+catName+"_"+catBreed+".png";
            pic_storage = storageReference.child(picture);
            pic_storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"上傳PASS",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }
            });
        }else {
            picture= "cat.png";
            dialog.dismiss();
            finish();
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
                img_addMyCat_picture.setImageBitmap(bitmap);
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