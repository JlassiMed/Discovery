package com.example.disocvery.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_COUNTRY_CAPITAL;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_COUNTRY_NAME;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.COLUMN_ID;
import static com.example.disocvery.DB.SQLiteContract.CountriesColumns.TABLE_COUNTRIES;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CountriesDb";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL statement to create COUNTRY table
        String SQL_CREATE_COUNTRIES_TABLE =
                "CREATE TABLE " + TABLE_COUNTRIES
                        + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_COUNTRY_NAME + " TEXT, "
                       // + COLUMN_COUNTRY_FLAG + " BLOB,"
                        + COLUMN_COUNTRY_CAPITAL + " TEXT);"
                        ;

        // Create countries table
        db.execSQL(SQL_CREATE_COUNTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // SQL statement to delete countries table
        String SQL_DELETE_COUNTRIES_TABLE = "DROP TABLE IF EXISTS countries;";

        // Drop older countries table if existed
        db.execSQL(SQL_DELETE_COUNTRIES_TABLE);

        // create fresh countries table
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }
}
