package com.example.disocvery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.disocvery.Country;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_ID;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.TABLE_COUNTRIES;


public class SQLiteOperations {
    SQLiteHelper sqliteHelper;

    public SQLiteOperations(Context context) {
        this.sqliteHelper = new SQLiteHelper(context);
    }

    // CRUD operations (create "add", read "get", update, delete) movie + get all movies + delete all movies

    public void addCountry(Country country) {

        // 1. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_COUNTRY_NAME, country.getCountry_name()); // get country name
        values.put(COLUMN_COUNTRY_CAPITAL, country.getCountry_capital()); // get capital
       // values.put(COLUMN_COUNTRY_FLAG, country.getCountry_flag()); // get flag
        // 2. get reference to writable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        // 3. insert
        db.insert(
                TABLE_COUNTRIES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Country getCountry(int id) {
        Country country = new Country();

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                COLUMN_ID,
                COLUMN_COUNTRY_NAME,
                COLUMN_COUNTRY_CAPITAL,
//                COLUMN_COUNTRY_FLAG
        };

        // Filter results WHERE "country_name" = 'mycountry'
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        // Optional - How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_ID + " DESC";

        // 1. get reference to readable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(
                TABLE_COUNTRIES, // The table to query
                projection, // The array of columns to return (pass null to get all)
                selection, // The columns for the WHERE clause
                selectionArgs, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null, // Sort order
                null); // Limit

        // 3. if we got results get the first one
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                // 4. build Country object
                country.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                country.setCountry_name(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME)));
                country.setCountry_capital(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_CAPITAL)));

             /*   byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_COUNTRY_FLAG));
                country.setCountry_flag(image);*/
                Log.d("getCountry(" + id + ")", country.toString());
            }
            cursor.close();
        }

        // 5. return Country
        return country;
    }

    // Get All Countries
    public List<Country> getAllCountries() {
        List<Country> countries = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM countries;";

        // 2. get reference to writable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            // 3. go over each row, build country and add it to list
            Country country = null;
            if (cursor.moveToFirst()) {
                do {
                    country = new Country();
                    country.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    country.setCountry_name(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME)));
                    country.setCountry_capital(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_CAPITAL)));
                /*    byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_COUNTRY_FLAG));
                    country.setCountry_flag(image);*/
                    // Add country to countries
                    countries.add(country);
                } while (cursor.moveToNext());
            }
            Log.d("getAllCountries()", countries.toString());
            cursor.close();
        }

        // return countries
        return countries;
    }

    // Updating single country
    public void updateCountry(Country country, String countryItemId) {

        // 1. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_COUNTRY_NAME, country.getCountry_name()); // get country name
        values.put(COLUMN_COUNTRY_CAPITAL, country.getCountry_capital()); // get capital
       // values.put(COLUMN_COUNTRY_FLAG, country.getCountry_flag()); // get flag
        // 2. get reference to writable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        // 3. updating row
        db.update(
                TABLE_COUNTRIES, //table
                values, // column/value
                COLUMN_ID + " = ?", // selections
                new String[]{countryItemId}); //selection args
//                new String[]{String.valueOf(position)}); //selection args

        // 4. close
        db.close();
    }

    // Deleting single country
    public void deleteCountry(Country country) {

        // 1. get reference to writable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        // 2. delete
        db.delete(
                TABLE_COUNTRIES,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(country.getId())});

        // 3. close
        db.close();
    }

    // Delete all countries
    public void deleteAllCountries() {
        // 1. get reference to writable com.example.disocvery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        db.delete(
                TABLE_COUNTRIES,
                null,
                null);
    }
}
