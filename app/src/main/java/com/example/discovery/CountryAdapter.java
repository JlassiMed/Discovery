package com.example.discovery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.disocvery.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Country> countryList;
    private Context context;
    private CountryItemClicked countryItemClicked;

    CountryAdapter(List<Country> countryList, Context context, CountryItemClicked countryItemClicked) {
        this.countryList = countryList;
        this.context = context;
        this.countryItemClicked = countryItemClicked;
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
        countriesViewHolder.itemView.setOnClickListener(v -> countryItemClicked.onCountryItemClicked(position));
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
        }
    }

    interface CountryItemClicked {
        void onCountryItemClicked(int position);
    }
}
