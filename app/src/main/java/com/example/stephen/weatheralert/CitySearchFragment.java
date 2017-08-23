package com.example.stephen.weatheralert;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stephen.weatheralert.adapter.CityListAdapter;
import com.example.stephen.weatheralert.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 18/08/2017.
 */

public class CitySearchFragment extends Fragment {

    RecyclerView recyclerView;

    CityListAdapter cityListAdapter;

    CitySearchFragmentListener mCallback;

    /**
     * Listen to City Search Result Events such as when a City is Clicked
     */
    public interface CitySearchFragmentListener {
        public void onCitySearchResultClicked(String string);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_search, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (CitySearchFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        cityListAdapter = new CityListAdapter(new ArrayList<>(0));
        cityListAdapter.setCityClickListener(new CityListAdapter.onCityClickListener() {

            @Override
            public void onItemClickListener(View view, int position) {
                if (position < cityListAdapter.getCities().size() &&
                        cityListAdapter.getCities().get(position) != null) {

                    //Communicate back to Activity using Listener
                    City city =  cityListAdapter.getCities().get(position);
                    mCallback.onCitySearchResultClicked(city.getName() + " WRYY!!");
                }
            }
        });
        recyclerView.setAdapter(cityListAdapter);
    }

    /**
     * Update the List of Search Results
     * @param cities
     */
    public void changeCityList(List<City> cities){
        cityListAdapter.updateList(cities);
    }
}
