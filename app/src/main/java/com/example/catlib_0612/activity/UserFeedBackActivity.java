package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.UserFeedback;
import com.example.catlib_0612.data.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFeedBackActivity extends AppCompatActivity {
    private String TAG = "UserFeedBackActivity";
    private TextView txt_userFeedback_account;
    private EditText edit_userFeedback_feedback;
    private Button btn_userFeedback_send;
    private SharedPreferences getPrefs;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String userName,userAccount,userPicture;
    private int userCunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back);
        initView();
    }

    private void initView(){
        userCunt = 0;
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserFeedback");

        txt_userFeedback_account = (TextView) findViewById(R.id.txt_userFeedback_account);
        txt_userFeedback_account.setText(userAccount);
        edit_userFeedback_feedback = (EditText) findViewById(R.id.edit_userFeedback_feedback);
        btn_userFeedback_send = (Button) findViewById(R.id.btn_userFeedback_send);
        btn_userFeedback_send.setOnClickListener(onClick);

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_userFeedback_send){
                Log.d(TAG, "onClick: btn_userFeedback_send");
                addUserFeedback();
                finish();
            }
        }
    };

    private void addUserFeedback(){
        Log.d(TAG, "addUserFeedback: ");
        String feedback = edit_userFeedback_feedback.getText().toString();
        UserFeedback userFeedback = new UserFeedback(userAccount,feedback);
        myRef.child("userFeedback"+userCunt).setValue(userFeedback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        userCunt = 0;
        readExistingData();
    }

    private void readExistingData(){
        Log.d(TAG, "readExistingData: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot.toString());
                    userCunt++;
                }
                Log.d(TAG, "onDataChange: userCunt = "+userCunt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}