package com.example.stephen.weatheralert.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stephen.weatheralert.R;
import com.example.stephen.weatheralert.model.City;

import java.util.List;

/**
 * Created by Stephen on 18/08/2017.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.LocationListViewHolder> {

    private List<City> cities;

    private onCityClickListener mItemClickListener;

    public List<City> getCities(){
        return cities;
    }

    public interface onCityClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setCityClickListener(onCityClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public void updateList(List<City> tempCities) {
        cities = tempCities;
        notifyDataSetChanged();
    }

    public interface Listener {
        void onLocationClicked(City city);
    }

    public CityListAdapter(List<City> cities) {

        this.cities = cities;
    }

    @Override
    public LocationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(LocationListViewHolder holder, int position) {
        if (cities != null && cities.size() > 0){

            City city = cities.get(position);

            String location = city.getName() + ", " + city.getCountry();
            holder.location_name.setText(location);

        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class LocationListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView location_name;

        public LocationListViewHolder(View itemView) {
            super(itemView);
            this.location_name = itemView.findViewById(R.id.tv_item_location_name);

            location_name.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null){
                mItemClickListener.onItemClickListener(view, getAdapterPosition());
            }
        }
    }
}
