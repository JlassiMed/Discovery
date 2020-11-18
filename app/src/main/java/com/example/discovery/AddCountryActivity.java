package com.example.discovery;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discovery.DB.SQLiteOperations;
import com.example.disocvery.R;

import static com.example.discovery.MainActivity.DB_ACTION;

public class AddCountryActivity extends AppCompatActivity {
    public static final String COUNTRY_INTENT_EXTRA = "COUNTRY";

    private EditText etCountryName;
    private EditText etCountryCapital;
    private Button btnAddUpdateCountry;

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
}