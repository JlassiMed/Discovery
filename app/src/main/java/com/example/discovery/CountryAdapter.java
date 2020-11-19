package com.example.discovery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.disocvery.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Country> countryList;
    private Context context;
    private static CountryItemClicked countryItemClicked;

    CountryAdapter(List<Country> countryList, Context context) {
        this.countryList = countryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Country countryItem = countryList.get(position);
        CountriesViewHolder countriesViewHolder = (CountriesViewHolder) holder;
        countriesViewHolder.tvCountryName.setText(countryItem.getCountryName());
        countriesViewHolder.tvCountryCapital.setText(countryItem.getCountryCapital());
        countriesViewHolder.tvCountryFlag.setImageURI(Uri.parse(countryItem.getCountryFlag()));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class CountriesViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName, tvCountryCapital;
        ImageView tvCountryFlag;

        CountriesViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.country_name);
            tvCountryCapital = itemView.findViewById(R.id.country_capital);
            tvCountryFlag = itemView.findViewById(R.id.country_flag);
            itemView.setOnClickListener(view -> countryItemClicked.onCountryItemClicked(itemView, CountriesViewHolder.this.getAdapterPosition()));
        }
    }

    interface CountryItemClicked {
        void onCountryItemClicked(View view, int position);
    }

    static void setCountryItemClickListener(CountryItemClicked countryItemClicked) {
        CountryAdapter.countryItemClicked = countryItemClicked;
    }
}
