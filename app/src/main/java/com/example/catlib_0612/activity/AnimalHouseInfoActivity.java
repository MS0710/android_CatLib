package com.example.catlib_0612.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AnimalHouseInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "AnimalHouseInfoActivity";
    private MapView mapView;
    private GoogleMap mMap;
    private int picture;
    private String title;
    private String phone;
    private String address;
    private String openTime;
    private String note;
    private double lat,lng;
    private ImageView img_animalHouseInfo_back,img_animalHouseInfo_picture;
    private TextView txt_animalHouseInfo_title,txt_animalHouseInfo_address,txt_animalHouseInfo_phone,
            txt_animalHouseInfo_openTime,txt_animalHouseInfo_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_house_info);

        mapView = findViewById(R.id.myMap);
        mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 沒有權限在此重新申請權限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }else{
            // 有權限了
            Log.d(TAG, "onCreate: 有權限了");
            mapView.getMapAsync(this);
        }

        initView();
    }

    private void initView(){
        Log.d(TAG, "initView: ");
        picture = getIntent().getIntExtra("picture",0);
        title = getIntent().getStringExtra("title");
        phone = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        openTime = getIntent().getStringExtra("openTime");
        note = getIntent().getStringExtra("note");
        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);

        img_animalHouseInfo_back = (ImageView) findViewById(R.id.img_animalHouseInfo_back);
        img_animalHouseInfo_picture = (ImageView) findViewById(R.id.img_animalHouseInfo_picture);
        txt_animalHouseInfo_title = (TextView) findViewById(R.id.txt_animalHouseInfo_title);
        txt_animalHouseInfo_address = (TextView) findViewById(R.id.txt_animalHouseInfo_address);
        txt_animalHouseInfo_phone = (TextView) findViewById(R.id.txt_animalHouseInfo_phone);
        txt_animalHouseInfo_openTime = (TextView) findViewById(R.id.txt_animalHouseInfo_openTime);
        txt_animalHouseInfo_note = (TextView) findViewById(R.id.txt_animalHouseInfo_note);

        img_animalHouseInfo_back.setOnClickListener(onClick);
        txt_animalHouseInfo_title.setText(title);
        img_animalHouseInfo_picture.setImageDrawable(getDrawable(picture));
        txt_animalHouseInfo_address.setText(address);
        txt_animalHouseInfo_phone.setText(phone);
        txt_animalHouseInfo_openTime.setText(openTime);
        txt_animalHouseInfo_note.setText(note);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onRequestPermissionsResult: YES");
            mapView.getMapAsync(this);
        }else {
            Log.d(TAG, "onRequestPermissionsResult: NO");
            Toast.makeText(AnimalHouseInfoActivity.this,"請開啟地圖權限",Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.img_animalHouseInfo_back){
                Log.d(TAG, "onClick: img_animalHouseInfo_back");
                finish();
            }

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //LatLng sydney = new LatLng(25.034396219803934, 121.56414149645977);
        LatLng sydney = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0F));


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}