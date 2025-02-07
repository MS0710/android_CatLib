package com.example.catlib_0612.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.activity.AnimalHouseActivity;
import com.example.catlib_0612.activity.CannedCatFoodActivity;
import com.example.catlib_0612.activity.CatBreedActivity;
import com.example.catlib_0612.activity.CatFoodItemInfoActivity;
import com.example.catlib_0612.activity.CatHealthSupplementsActivity;
import com.example.catlib_0612.activity.CatLitterActivity;
import com.example.catlib_0612.activity.CatPersonalityActivity;
import com.example.catlib_0612.activity.CommonDiseaseActivity;
import com.example.catlib_0612.activity.DryFeedActivity;
import com.example.catlib_0612.activity.SearchActivity;
import com.example.catlib_0612.adapter.HomeItemAdapter;
import com.example.catlib_0612.data.HomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "MainFragment";
    private GridView gv_home_list;
    private HomeItemAdapter homeItemAdapter;
    private List<HomeItem> list;
    private String[] title = {"貓咪品種","乾飼料","罐頭","凍乾主食糧","風乾主食糧",
            "生食", "寵物蛋糕與零食","常見疾病","保健食品","貓咪個性與行為","貓砂","動保機構",};
    private EditText edit_home_input;
    private ImageView img_home_search;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        edit_home_input = (EditText) view.findViewById(R.id.edit_home_input);
        img_home_search = (ImageView) view.findViewById(R.id.img_home_search);
        img_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_home_input.getText().toString().equals("")){
                    Toast.makeText(getContext(),"請輸入搜尋",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra("searchItem",edit_home_input.getText().toString());
                    startActivity(intent);
                }
            }
        });

        gv_home_list = (GridView) view.findViewById(R.id.gv_home_list);
        list = new ArrayList<>();
        setData();

        homeItemAdapter = new HomeItemAdapter(getContext(),list);
        gv_home_list.setAdapter(homeItemAdapter);
        gv_home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (list.get(i).getTitle()){
                    case "貓咪品種":
                        intent = new Intent(getContext(), CatBreedActivity.class);
                        startActivity(intent);
                        break;
                    case "乾飼料":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "DryCat");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), DryFeedActivity.class);
                        intent.putExtra("Title", "乾飼料");
                        startActivity(intent);
                        break;
                    case "罐頭":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "CannedCat");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CannedCatFoodActivity.class);
                        intent.putExtra("Title", "罐頭");
                        startActivity(intent);
                        break;
                    case "凍乾主食糧":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "FreezeDriedCat");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Freeze_Dried_Cat_Food");
                        intent.putExtra("Title", "凍乾主食糧");
                        startActivity(intent);
                        break;
                    case "風乾主食糧":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "AirDriedCat");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Air_Dried_Cat_Food");
                        intent.putExtra("Title", "風乾主食糧");
                        startActivity(intent);
                        break;
                    case "生食":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "RawFood");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Raw_Food");
                        intent.putExtra("Title", "生食");
                        startActivity(intent);
                        break;
                    case "寵物蛋糕與零食":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "PetCakesAndTreats");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CatFoodItemInfoActivity.class);
                        intent.putExtra("TitleTag", "Pet_Cakes_and_Treats");
                        intent.putExtra("Title", "寵物蛋糕與零食");
                        startActivity(intent);
                        break;
                    case "保健食品":
                        getPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = getPrefs.edit();
                        editor.putString("Feed_lv1", "HealthSupplements");
                        editor.putString("Feed_lv2", "");
                        editor.putString("Feed_lv3", "");
                        editor.putString("Feed_lv4", "");
                        editor.putString("Feed_lv5", "");
                        editor.putString("Feed_lv6", "");
                        editor.apply();
                        intent = new Intent(getContext(), CatHealthSupplementsActivity.class);
                        startActivity(intent);
                        break;
                    case "常見疾病":
                        intent = new Intent(getContext(), CommonDiseaseActivity.class);
                        startActivity(intent);
                        break;
                    case "貓咪個性與行為":
                        intent = new Intent(getContext(), CatPersonalityActivity.class);
                        startActivity(intent);
                        break;
                    case "貓砂":
                        intent = new Intent(getContext(), CatLitterActivity.class);
                        startActivity(intent);
                        break;
                    case "動保機構":
                        intent = new Intent(getContext(), AnimalHouseActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getContext(),"position"+i, Toast.LENGTH_SHORT).show();
                        break;
                }
                //Intent intent = new Intent(getApplicationContext(), AnimalHouseInfoActivity.class);
                //startActivity(intent);
            }
        });



        return view;
    }

    private void setData(){
        for (int i=0; i< title.length; i++){
            list.add(new HomeItem(title[i]));
        }
    }
}