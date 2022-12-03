package com.darshan09200.restaurantorderingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    MealsAdapter mealsAdapter;
    ListView mealsListView;

    FloatingActionButton cartFab;
    TextView badgeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mealsListView = findViewById(R.id.mealsListView);
        cartFab = findViewById(R.id.cartFab);
        badgeCount = findViewById(R.id.badgeCount);

        mealsAdapter = new MealsAdapter(this, R.layout.meal, DataType.ALL);
        mealsListView.setAdapter(mealsAdapter);

        cartFab.setOnClickListener(v -> {
            Toast.makeText(this, "Total cart count: " + Restaurant.getInstance().getCartItems().size(), Toast.LENGTH_SHORT).show();
        });

        Restaurant.getInstance().registerBadge(badgeCount);
        Restaurant.getInstance().updateBadge();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Restaurant.getInstance().unregisterBadge();
    }
}