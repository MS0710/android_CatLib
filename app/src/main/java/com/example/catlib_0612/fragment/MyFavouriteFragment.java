package com.example.catlib_0612.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.activity.CatFoodContentActivity;
import com.example.catlib_0612.adapter.FeedAdapter;
import com.example.catlib_0612.data.Feed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavouriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "MyFavouriteFragment";
    private GridView gv_myFavourite_list;
    private List<Feed> list;
    private FeedAdapter feedAdapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SharedPreferences getPrefs;
    private String userName,userAccount,userPicture;

    public MyFavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavouriteFragment newInstance(String param1, String param2) {
        MyFavouriteFragment fragment = new MyFavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favourite, container, false);
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        userName = getPrefs.getString("userName", "");
        userAccount = getPrefs.getString("userAccount", "");
        userPicture = getPrefs.getString("userPicture", "");
        Log.d(TAG, "onCreateView: userPicture = "+userPicture);
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);
        userAccount = userAccount.replace(".", "");
        Log.d(TAG, "onCreateView: userAccount = "+userAccount);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserMyFavourite");

        gv_myFavourite_list = (GridView) view.findViewById(R.id.gv_myFavourite_list);
        list = new ArrayList<>();

        feedAdapter = new FeedAdapter(getContext(),list);
        gv_myFavourite_list.setAdapter(feedAdapter);
        gv_myFavourite_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CatFoodContentActivity.class);
                intent.putExtra("Title", "我的最愛");
                intent.putExtra("tag", list.get(i).getTAG());
                intent.putExtra("picture", list.get(i).getPicture());
                intent.putExtra("brand", list.get(i).getBrand());
                intent.putExtra("name", list.get(i).getName());
                intent.putExtra("introduce", list.get(i).getIntroduce());
                intent.putExtra("element", list.get(i).getElement());
                startActivity(intent);
            }
        });




        return view;
    }

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
                String curTAG,picture,brand,name,introduce,element;

                Feed feed = null;
                list.clear();
                //title = dataSnapshot.child("house"+1).child("title").getValue().toString();
                //Log.d(TAG, "onDataChange: title = "+title);
                for (DataSnapshot postSnapshot : dataSnapshot.child(userAccount).getChildren()){
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
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}