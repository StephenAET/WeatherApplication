package com.example.stephen.weatheralert;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Stephen on 22/08/2017.
 */

public class CityDetailFragment extends Fragment {

    TextView textView;

    ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        imageView = view.findViewById(R.id.detail_backdrop);
        Picasso.with(getContext())
                .load("http://lorempixel.com/400/200/")
                .fit()
                .into(imageView);*/

        TabLayout tabLayout = view.findViewById(R.id.detail_tabs);

        //NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView2);
        //nestedScrollView.setFillViewport(true);

        CityDetailPagerAdapter adapter = new CityDetailPagerAdapter(
                getActivity().getSupportFragmentManager(), 2);

        ViewPager viewPager = view.findViewById(R.id.detail_pager);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
