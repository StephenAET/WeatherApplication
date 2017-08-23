package com.example.stephen.weatheralert.model;

/**
 * Created by Stephen on 18/08/2017.
 */

public class City {

    int id;
    String name;
    String country;
    //Coord coord;

    public City(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
