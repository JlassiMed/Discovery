package com.example.discovery.DB;

import android.provider.BaseColumns;

public class SQLiteContract {
    private SQLiteContract() {
    }

    // Inner class that defines the table contents. Create a class for every table
    static class CountriesColumns implements BaseColumns {
        // Table Name
        static final String TABLE_COUNTRIES = "countries";
        // Table Column Names
        static final String COLUMN_ID = "id";
        static final String COLUMN_COUNTRY_NAME = "country_name";
        static final String COLUMN_COUNTRY_CAPITAL = "country_capital";
       // static final String COLUMN_COUNTRY_FLAG = "country_flag";

    }
}
