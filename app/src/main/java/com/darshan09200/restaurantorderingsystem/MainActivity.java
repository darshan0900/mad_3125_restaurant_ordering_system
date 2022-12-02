package com.darshan09200.restaurantorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    MealsAdapter mealsAdapter;
    ListView mealsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mealsListView = findViewById(R.id.mealsListView);

        mealsAdapter = new MealsAdapter(this, R.layout.meal, Meals.getInstance().getMeals());
        mealsListView.setAdapter(mealsAdapter);
    }
}