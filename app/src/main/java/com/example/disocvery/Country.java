package com.example.disocvery;
import android.media.Image;

import java.io.Serializable;

public class Country implements Serializable {
private int id;
private String country_name;
private String country_capital;
//private byte[] country_flag;

    public Country() {
    }

    public Country(String country_name, String country_capital) {

        this.country_name = country_name;
        this.country_capital = country_capital;
      //  this.country_flag = country_flag;
    }

    public int getId() {
        return id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCountry_capital() {
        return country_capital;
    }

    /*public byte[] getCountry_flag() {
        return country_flag;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setCountry_capital(String country_capital) {
        this.country_capital = country_capital;
    }

 //   public void setCountry_flag(byte[] country_flag) {
  //      this.country_flag = country_flag;
   // }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", country_name='" + country_name + '\'' +
                ", country_capital='" + country_capital + '\'' +
                '}';
    }
}
