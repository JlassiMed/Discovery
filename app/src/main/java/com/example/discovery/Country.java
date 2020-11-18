package com.example.discovery;

import java.io.Serializable;

public class Country implements Serializable {
    private int id;
    private String countryName;
    private String countryCapital;

    public Country() {
    }

    public Country(String countryCapital, String countryName) {
        this.countryName = countryName;
        this.countryCapital = countryCapital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }
}
