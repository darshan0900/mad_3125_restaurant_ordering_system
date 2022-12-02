package com.darshan09200.restaurantorderingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

public class MealsAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    private final int layoutResource;

    private List<Meal> meals;

    public MealsAdapter(@NonNull Context context, int resource, @NonNull List<Meal> meals) {
        super(context, resource);

        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = layoutInflater.inflate(layoutResource, parent, false);
        TextView mealTitle = v.findViewById(R.id.mealTitle);
        ImageView mealImage = v.findViewById(R.id.mealImage);
        Button addToCartButton = v.findViewById(R.id.addToCart);
        LinearLayout counterLayout = v.findViewById(R.id.counterButton);

        mealTitle.setText("Test");
        mealImage.setImageResource(R.drawable.ic_launcher_foreground);
        if (position % 2 == 0) {
            addToCartButton.setVisibility(View.GONE);
            counterLayout.setVisibility(View.VISIBLE);
        } else {
            addToCartButton.setVisibility(View.VISIBLE);
            counterLayout.setVisibility(View.GONE);
        }
        return v;
    }
}
