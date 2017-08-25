package com.example.stephen.weatheralert;


import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.stephen.weatheralert.model.City;
import com.example.stephen.weatheralert.mvp.CityListTask;

import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends AppCompatActivity implements CitySearchFragment.CitySearchFragmentListener {

    SearchView searchView;
    List<City> cities = new ArrayList<>(0);
    ProgressBar progressBar;

    CityListTask cityListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        //Register Progress Bar in Layout
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Begin threading Cities List retrieval
        cityListTask = new CityListTask(this);
        cityListTask.execute();

        //Register and Setup Toolbar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        /*
        //Register and Setup Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Register and Setup Search Bar
        searchView = (SearchView) findViewById(R.id.search_view);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Find Cities"); //Indicate visually what the search bar does
        searchView.setIconifiedByDefault(true); //Search bar is collapsed by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                List<City> filteredCityList = new ArrayList<>();
                for (City city : cities) {

                    //Compare the name of the city to the query provided
                    if (city.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredCityList.add(city);
                    }
                }
                displaySearchResults(filteredCityList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //TODO Try Filtering whilst typing
                return true;
            }
        });
    }

    /**
     * Get the List of Cities from the City List Task
     *
     * @param cities
     */
    public void onCityListTask(List<City> cities) {
        this.cities = cities;
        progressBar.setVisibility(View.GONE);
    }

    /* THIS WONT WORK
    class FilterCitiesTask extends AsyncTask<String, Void, List<City>> {

        @Override
        protected List<City> doInBackground(String... strings) {
            List<City> filteredCityList = new ArrayList<>();
            for (City city : cities) {

                //Compare the name of the city to the query provided
                if (city.getName().toLowerCase().contains(strings[0].toLowerCase())) {
                    filteredCityList.add(city);
                }
            }
            return filteredCityList;
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            CitiesActivity.this.displaySearchResults(cities);
        }
    }*/

    /**
     * Display a list of (filtered) Cities in the City Search List Fragment
     *
     * @param cities
     */
    private void displaySearchResults(List<City> cities) {

        //Attempt to Find the City Search List Fragment in the Container
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.cities_container);
        if (fragment != null && fragment.getClass() == CitySearchFragment.class) {
            //Fragment already exists in container, so just update
            ((CitySearchFragment) fragment).changeCityList(cities);
        } else {

            //Create, Present and Update the fragment
            fragment = new CitySearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cities_container, fragment)
                    .commit();
            //fragment.changeCityList(cities);
        }
    }

    /**
     * Inflate Locations Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locations, menu);
        return true;
    }

    /**
     * Handle Action Bar Item Click Events
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Cancel Async Tasks
     */
    @Override
    protected void onDestroy() {
        cityListTask.cancel(true);
        super.onDestroy();
    }

    /**
     * Callback from City Search Fragment
     * When a City Search Result is Clicked in the City Search List Fragment
     *
     * @param searchText
     */
    @Override
    public void onCitySearchResultClicked(String searchText) {

        Bundle bundle = new Bundle();
        bundle.putString("city", searchText);

        CityDetailFragment fragment = new CityDetailFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cities_container, fragment)
                .commit();
    }

    /**
     * Update the Progress Bar Value
     *
     * @param progressPercent
     */
    public void setProgressPercent(Integer progressPercent) {
        progressBar.setProgress(progressPercent);
    }
}
