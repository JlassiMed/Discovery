package com.example.disocvery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CountriesAdapter";

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

    // convert byte[] to bitmap image
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Country countryItem = countryList.get(position);
        if (null != holder) {
            CountriesViewHolder countriesViewHolder = (CountriesViewHolder) holder;
            countriesViewHolder.tvCountryName.setText(countryItem.getCountry_name());
            countriesViewHolder.tvCountryCapital.setText(countryItem.getCountry_capital());
          /*  byte[] image = countryItem.getCountry_flag();
            countriesViewHolder.tvCountryFlag.setImageBitmap(getImage(image));*/
        }
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class CountriesViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountryName, tvCountryCapital;
        ImageView tvCountryFlag;

        CountriesViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.country_name);
            tvCountryCapital = itemView.findViewById(R.id.country_capital);
            tvCountryFlag= itemView.findViewById(R.id.country_flag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countryItemClicked.onCountryItemClicked(itemView, CountriesViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    interface CountryItemClicked {
        void onCountryItemClicked(View view, int position);
    }

     static void setCountryItemClickListener(CountryItemClicked countryItemClicked) {
        CountryAdapter.countryItemClicked = countryItemClicked;
    }
}
