package com.example.discovery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.discovery.DB.SQLiteOperations;
import com.example.disocvery.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static com.example.discovery.MainActivity.COUNTRY_ITEM_ID_EXTRA;
import static com.example.discovery.MainActivity.DB_ACTION;

public class AddCountryActivity extends AppCompatActivity {
    public static final String COUNTRY_INTENT_EXTRA = "COUNTRY";
    private static final int GALLERY_REQUEST = 100;

    private EditText etCountryName;
    private EditText etCountryCapital;
    private ImageView countryFlag;
    private Uri uri;

    private String countryFlagPath;
    public CrudMethod crudMethod;
    private int CountryItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);

        etCountryName = findViewById(R.id.country_name);
        etCountryCapital = findViewById(R.id.country_capital);
        countryFlag = findViewById(R.id.country_flag_get);
        Button btnAddUpdateCountry = findViewById(R.id.btn_add_country);
        Button btnAddCountryFlag = findViewById(R.id.btn_add_flag);


        btnAddCountryFlag.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });


        SQLiteOperations sqliteOperations = new SQLiteOperations(this);

        Intent intent = getIntent();
        crudMethod = (CrudMethod) intent.getSerializableExtra(DB_ACTION);
        if (crudMethod != null) {
            switch (crudMethod) {
                case ADD:
                    btnAddUpdateCountry.setText(getString(R.string.add_country_text));
                    break;

                case UPDATE:
                    btnAddUpdateCountry.setText(getString(R.string.update_country_text));
                    CountryItemId = intent.getIntExtra(COUNTRY_ITEM_ID_EXTRA, 0);
                    Country countryItem = sqliteOperations.getCountry(CountryItemId);
                    etCountryName.setText(countryItem.getCountryName());
                    etCountryCapital.setText(countryItem.getCountryCapital());
                    countryFlag.setImageURI(Uri.parse(new File(countryItem.getCountryFlag()).toString()));
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

            Country countryItem = new Country();
            countryItem.setCountryCapital(capital);
            countryItem.setCountryName(name);
            if (uri != null) {
                String filename = uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1);
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if (storageDir != null) {
                    countryFlagPath = storageDir.getAbsolutePath() +"/"+ filename + ".jpg";
                    File file = new File(storageDir.getAbsolutePath(), filename);
                    try {
                        FileOutputStream out = new FileOutputStream(file + ".jpg");
                        createImageFromUri(uri).compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        countryItem.setCountryFlag(countryFlagPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(COUNTRY_INTENT_EXTRA, countryItem);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Flag required !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCountry() {
        if (hasValidInput(etCountryName, etCountryCapital)) {
            String name = etCountryName.getText().toString();
            String capital = etCountryCapital.getText().toString();
            Country countryItem = new Country(capital, name, countryFlagPath);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("COUNTRY", countryItem);
            intent.putExtra("COUNTRY_ID", String.valueOf(CountryItemId));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private Bitmap createImageFromUri(Uri uri) throws FileNotFoundException {
        final InputStream imageStream = getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(imageStream);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            try {
                final Uri uri = data.getData();
                if (uri != null) {
                    this.uri = uri;
                    countryFlag.setImageBitmap(createImageFromUri(uri));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}