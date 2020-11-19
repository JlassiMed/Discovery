package com.example.discovery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.discovery.Country;

import java.util.LinkedList;
import java.util.List;


public class SQLiteOperations {
    SQLiteHelper sqliteHelper;

    public SQLiteOperations(Context context) {
        this.sqliteHelper = new SQLiteHelper(context);
    }

    public void addCountry(Country country) {

        ContentValues values = new ContentValues();
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME, country.getCountryName());
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL, country.getCountryCapital());
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_FLAG, country.getCountryFlag());
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        db.insert(
                SQLiteContract.CountriesColumns.TABLE_COUNTRIES,
                null,
                values);
        db.close();
    }

    public Country getCountry(int id) {
        Country country = new Country();

        String[] projection = {
                SQLiteContract.CountriesColumns.COLUMN_ID,
                SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME,
                SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL,
                SQLiteContract.CountriesColumns.COLUMN_COUNTRY_FLAG
        };

        String selection = SQLiteContract.CountriesColumns.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = SQLiteContract.CountriesColumns.COLUMN_ID + " DESC";
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SQLiteContract.CountriesColumns.TABLE_COUNTRIES, // The table to query
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
                country.setId(cursor.getInt(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_ID)));
                country.setCountryName(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME)));
                country.setCountryCapital(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL)));
                country.setCountryFlag(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_FLAG)));
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
        String query = "SELECT * FROM countries;";

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            Country country;
            if (cursor.moveToFirst()) {
                do {
                    country = new Country();
                    country.setId(cursor.getInt(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_ID)));
                    country.setCountryName(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME)));
                    country.setCountryCapital(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL)));
                    country.setCountryFlag(cursor.getString(cursor.getColumnIndex(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_FLAG)));
                    countries.add(country);
                } while (cursor.moveToNext());
            }
            Log.d("getAllCountries()", countries.toString());
            cursor.close();
        }
        return countries;
    }

    // Updating single country
    public void updateCountry(Country country) {
        ContentValues values = new ContentValues();
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME, country.getCountryName());
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL, country.getCountryCapital());
        values.put(SQLiteContract.CountriesColumns.COLUMN_COUNTRY_FLAG, country.getCountryFlag());
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        String[] selectionArgs = { "" + country.getId() };
        db.update(
                SQLiteContract.CountriesColumns.TABLE_COUNTRIES,
                values,
                SQLiteContract.CountriesColumns.COLUMN_ID + " = ?",
                selectionArgs);
        db.close();
    }

    public void deleteCountry(Country country) {

        // 1. get reference to writable com.example.discovery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        // 2. delete
        db.delete(
                SQLiteContract.CountriesColumns.TABLE_COUNTRIES,
                SQLiteContract.CountriesColumns.COLUMN_ID + " = ?",
                new String[]{String.valueOf(country.getId())});

        // 3. close
        db.close();
    }

    // Delete all countries
    public void deleteAllCountries() {
        // 1. get reference to writable com.example.discovery.DB
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        db.delete(
                SQLiteContract.CountriesColumns.TABLE_COUNTRIES,
                null,
                null);
    }
}
