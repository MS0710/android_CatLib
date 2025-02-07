package com.example.catlib_0612.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.MainPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "HomeFragment";
    private SmartTabLayout smartTabLayout ;
    private ViewPager viewpager;
    private MainPagerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        smartTabLayout = (SmartTabLayout)view.findViewById(R.id.viewpagertab);
        viewpager = (ViewPager)view.findViewById(R.id.viewpager);

        //MainPagerAdapter adapter = new MainPagerAdapter(getContext().getSupportFragmentManager());
        adapter = new MainPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MainFragment(),"Main");
        adapter.addFragment(new MyFavouriteFragment(),"MyFavour");
        adapter.addFragment(new RecordFragment(),"Record");
        viewpager.setOffscreenPageLimit(3);

        //final LayoutInflater inflater2 = LayoutInflater.from(getContext());
        final int[] tabIcons = {R.drawable.tab_ic_home, R.drawable.tab_ic_me, R.drawable.tab_ic_orders};
        final int[] tabTitles = {R.string.tab_home, R.string.tab_orders, R.string.tab_me};
        smartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.layout_navigation_bottom_item, container, false);
                ImageView iconView = (ImageView) view.findViewById(R.id.img_icon);
                Log.d(TAG, "createTabView: position = "+position);
                Log.d(TAG, "createTabView: tabIcons.length = "+tabIcons.length);
                Log.d(TAG, "createTabView: position % tabIcons.length = "+(position % tabIcons.length));
                iconView.setBackgroundResource(tabIcons[position % tabIcons.length]);
                TextView titleView = (TextView) view.findViewById(R.id.txt_title);
                titleView.setText(tabTitles[position % tabTitles.length]);
                //////////
                //titleView.setVisibility(View.GONE);
                /*(position == 1){
                    ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.FILL_PARENT;
                    //layoutParams.height = 200;
                    iconView.setLayoutParams(layoutParams);
                    titleView.setVisibility(View.GONE);
                }*/
                /*if(position != 1){
                    //TextView titleView = (TextView) view.findViewById(R.id.txt_title);
                    titleView.setText(tabTitles[position % tabTitles.length]);
                }*/
                return view;
            }
        });

        viewpager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewpager);


        return view;
    }
}