package com.example.stephen.weatheralert.mvp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;

import com.example.stephen.weatheralert.CitiesActivity;
import com.example.stephen.weatheralert.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 18/08/2017.
 */

public class CityListTask extends AsyncTask<Void, Integer, List<City>> {

    private Activity activity;

    public CityListTask(Activity activity) {
        this.activity = activity;
    }

    public String loadJSONFromAsset() {

        String json = null;
        try {
            InputStream is = activity.getAssets().open("city.list.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public City readCity(JsonReader reader) throws IOException {

        String name = "";
        int id = 0;
        String country = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String token = reader.nextName();


            switch (token){
                case "id" : id = reader.nextInt(); break;
                case "name" : name = reader.nextString(); break;
                case "country" : country = reader.nextString(); break;
                default: reader.skipValue();
            }

            /*
            if ("id".equals(token)) {

            } else if ("name".equals(token)) {
                name = reader.nextString();
            } else if ("country".equals(token))
                reader.skipValue();*/
        }
        reader.endObject();

        return new City(id, name, country);
    }


    private List<City> getCities() {

        List<City> cities = new ArrayList<>(0);

        try {

            InputStream is = activity.getAssets().open("city.list.json");

            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            reader.beginArray();
            while (reader.hasNext()) {
                cities.add(readCity(reader));
            }
            reader.endArray();


            //JsonFactory jsonFactory = new JsonFactory();
            //JsonParser parser = jsonFactory.createJsonParser(is);

            /*
            while (parser.nextToken() != JsonToken.END_ARRAY) {

                String token = parser.getCurrentName();

                if ("id".equals(token)) {
                    parser.nextToken();
                    int id = parser.getIntValue();
                    System.out.println(id);
                }
                if ("name".equals(token)) {
                    parser.nextToken();
                    String name = parser.getText();
                    System.out.println(name);
                }
                parser.close();
            }
        } catch (JsonGenerationException jge) {
            jge.printStackTrace();
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }*/

        /*
        try {
            String jsonString = loadJSONFromAsset();
            if (jsonString != null && !jsonString.isEmpty()) {

                JSONArray jsonArray = new JSONArray(jsonString);
                if (jsonArray != null && jsonArray.length() > 0) {

                    int numberOfCities = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String country = jsonObject.getString("country");

                            JSONObject coordJsonObject = jsonObject.getJSONObject("coord");
                            long lon = coordJsonObject.getLong("lon");
                            long lan = coordJsonObject.getLong("lat");

                            City city = new City(id, name, country, new Coord(lon, lan));
                            cities.add(city);

                            publishProgress(numberOfCities-i);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        } catch (IOException e) {

        }
        return cities;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        ((CitiesActivity) activity).setProgressPercent(values[0]);
    }

    @Override
    protected List<City> doInBackground(Void... voids) {
        return getCities();
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        ((CitiesActivity) activity).onCityListTask(cities);
        super.onPostExecute(cities);
    }
}
