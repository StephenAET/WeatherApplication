package com.example.stephen.weatheralert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Stephen on 24/08/2017.
 */

public class CityDetailPagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;

    public CityDetailPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                CityDetailTodayFragment cityDetailTodayFragment = new CityDetailTodayFragment();
                return cityDetailTodayFragment;

            case 1:
                CityDetailForecastFragment cityDetailForecastFragment = new CityDetailForecastFragment();
                return cityDetailForecastFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB " + (position + 1);
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
