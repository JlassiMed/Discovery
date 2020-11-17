package com.example.disocvery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.disocvery.DB.SQLiteOperations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

public class AddCountry extends AppCompatActivity {
    private static final String TAG = "AddCountryActivity";

    private EditText etCountryName;
    private EditText etCountryCapital;
   // private ImageView etCountryFlag; // *******************************
    private Button btnAddUpdateCountry;

    public String dbAction;
    private SQLiteOperations sqliteOperations;
    private int CountryItemId;

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);
        initializeViews();
        initializeData();
        getIntentData();
        clickListeners();
    }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setStatuBarColor(Activity activity, int statusBarColor) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
            window.requestFeature(window.FEATURE_NO_TITLE);
            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        private void initializeViews() {
            etCountryName = findViewById(R.id.country_name);
            etCountryCapital = findViewById(R.id.country_capital);
         //   etCountryFlag= findViewById(R.id.country_flag);
            btnAddUpdateCountry = findViewById(R.id.btn_add_country);
        }

        private void initializeData() {
            sqliteOperations = new SQLiteOperations(this);
        }

        private void getIntentData() {
            Intent intent = getIntent();
            dbAction = intent.getStringExtra("DB_ACTION");
            if (("add").equals(dbAction)) {
                btnAddUpdateCountry.setText("ADD Country");
            }

            if (("update").equals(dbAction)) {
                btnAddUpdateCountry.setText("UPDATE Country");
                CountryItemId = intent.getIntExtra("COUNTRY_ITEM_ID", 0);
                Log.d(TAG, "getIntentData: item id: " + CountryItemId);
                Country CountryItem = sqliteOperations.getCountry(CountryItemId);
                etCountryName.setText(CountryItem.getCountry_name());
                etCountryCapital.setText(CountryItem.getCountry_capital());
             //   etCountryFlag.setImageBitmap(getImage((CountryItem.getCountry_flag())));
            }
        }

        private void clickListeners() {
            btnAddUpdateCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (("add").equals( dbAction)) {
                        AddCountry.this.addCountry();
                    }

                    if (("update").equals(dbAction)) {
                        AddCountry.this.updateCountry();
                    }
                }
            });
        }

        private boolean hasValidInput(EditText etMovieTitle, EditText etMovieDirector) {
            String movieTitle = String.valueOf(etMovieTitle.getText());
            String movieDirector = String.valueOf(etMovieDirector.getText());

            if (("").equals(movieTitle)) {
                etMovieTitle.setError("Title Required!");
                etMovieTitle.requestFocus();
                return false;
            }

            if (("").equals(movieDirector)) {
                etMovieDirector.setError("Director Required!");
                etMovieDirector.requestFocus();
                return false;
            }

            return true;
        }

        private void addCountry() {
            if (hasValidInput(etCountryName, etCountryCapital)) {
                String name = String.valueOf(etCountryName.getText());
                String capital = String.valueOf(etCountryCapital.getText());
              /*  etCountryFlag.invalidate();
                File imgFile = new  File("mipmap-hdpi/ic_launcher.png");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                /*BitmapDrawable drawable = (BitmapDrawable) imgFile.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();*/
               // myBitmap.recycle();
                Country countryItem = new Country(name, capital);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("COUNTRY", countryItem);
                setResult(RESULT_OK, intent);
                Log.d(TAG,"*******************adeeed success"+name+capital);
                finish();
            }
        }

        private void updateCountry() {
            if (hasValidInput(etCountryName, etCountryCapital)) {
                String name = String.valueOf(etCountryName.getText());
                String capital = String.valueOf(etCountryCapital.getText());
             /*   etCountryFlag.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) etCountryFlag.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmap.recycle();*/
                Country countryItem = new Country(name, capital);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("COUNTRY", countryItem);
                intent.putExtra("COUNTRY_ID", String.valueOf(CountryItemId));
                setResult(RESULT_OK, intent);
                finish();
            }
    }
}