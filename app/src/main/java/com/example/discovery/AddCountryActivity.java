package com.example.discovery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.discovery.DB.SQLiteOperations;
import com.example.disocvery.R;

import static com.example.discovery.MainActivity.DB_ACTION;

public class AddCountryActivity extends AppCompatActivity {
    public static final String COUNTRY_INTENT_EXTRA = "COUNTRY";
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private EditText etCountryName;
    private EditText etCountryCapital;
    private Button btnAddUpdateCountry;
    private ImageView countryFlag;

    public CrudMethod crudMethod;
    private SQLiteOperations sqliteOperations;
    private int CountryItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);

        etCountryName = findViewById(R.id.country_name);
        etCountryCapital = findViewById(R.id.country_capital);
        btnAddUpdateCountry = findViewById(R.id.btn_add_country);
        countryFlag = findViewById(R.id.country_flag_get);


        countryFlag.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        sqliteOperations = new SQLiteOperations(this);

        Intent intent = getIntent();
        crudMethod = (CrudMethod) intent.getSerializableExtra(DB_ACTION);
        if (crudMethod != null) {
            switch (crudMethod) {
                case ADD:
                    btnAddUpdateCountry.setText(getString(R.string.add_country_text));
                    break;

                case UPDATE:
                    btnAddUpdateCountry.setText(getString(R.string.update_country_text));
                    CountryItemId = intent.getIntExtra("COUNTRY_ITEM_ID", 0);
                    Country CountryItem = sqliteOperations.getCountry(CountryItemId);
                    etCountryName.setText(CountryItem.getCountryName());
                    etCountryCapital.setText(CountryItem.getCountryCapital());
                    break;
            }

            btnAddUpdateCountry.setOnClickListener(view -> {
                switch (crudMethod) {
                    case ADD:
                        AddCountryActivity.this.addCountry();
                        break;

                    case UPDATE:
                        AddCountryActivity.this.updateCountry();
                        break;
                }
            });
        }
    }

    private boolean hasValidInput(EditText etMovieTitle, EditText etMovieDirector) {
        String movieTitle = etMovieTitle.getText().toString();
        String movieDirector = etMovieDirector.getText().toString();
        if (movieTitle.isEmpty()) {
            etMovieTitle.setError("Required field!");
            etMovieTitle.requestFocus();
            return false;
        }

        if (movieDirector.isEmpty()) {
            etMovieDirector.setError("Required field!");
            etMovieDirector.requestFocus();
            return false;
        }
        return true;
    }

    private void addCountry() {
        if (hasValidInput(etCountryName, etCountryCapital)) {
            String name = etCountryName.getText().toString();
            String capital = etCountryCapital.getText().toString();

            Country countryItem = new Country(name, capital);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(COUNTRY_INTENT_EXTRA, countryItem);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void updateCountry() {
        if (hasValidInput(etCountryName, etCountryCapital)) {
            String name = String.valueOf(etCountryName.getText());
            String capital = String.valueOf(etCountryCapital.getText());
            Country countryItem = new Country(name, capital);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("COUNTRY", countryItem);
            intent.putExtra("COUNTRY_ID", String.valueOf(CountryItemId));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            countryFlag.setImageBitmap(photo);
        }
    }
}