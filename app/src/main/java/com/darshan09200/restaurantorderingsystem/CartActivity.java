package com.darshan09200.restaurantorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartActivity extends AppCompatActivity implements CartUpdateListener {

    MealsAdapter cartAdapter;
    ListView cartListView;
    TextView subtotalAmount, taxes, totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);

        cartAdapter = new MealsAdapter(this, R.layout.meal, DataType.CART);
        cartListView.setAdapter(cartAdapter);
        cartListView.setEmptyView(findViewById(R.id.noRecords));
        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        cartListView.addFooterView(footerView);

        subtotalAmount = footerView.findViewById(R.id.subtotalAmount);
        taxes = footerView.findViewById(R.id.taxes);
        totalAmount = footerView.findViewById(R.id.totalAmount);

        recalculate();

        footerView.findViewById(R.id.confirm).setOnClickListener(v -> {
            Restaurant.getInstance().resetCartItems();
            CartActivity.this.finish();
        });
    }

    @Override
    public void recalculate() {
        double subtotalPrice = 0;
        for (int i = 0; i < cartAdapter.getCount(); i++) {
            Meal meal = cartAdapter.getItem(i);
            subtotalPrice += meal.getPrice() * meal.getCartQuantity();
        }

        double taxesPrice = subtotalPrice * 0.13;
        double totalPrice = subtotalPrice + taxesPrice;

        subtotalAmount.setText(String.format("$ %.2f", subtotalPrice));
        taxes.setText(String.format("$ %.2f", taxesPrice));
        totalAmount.setText(String.format("$ %.2f", totalPrice));
    }
}