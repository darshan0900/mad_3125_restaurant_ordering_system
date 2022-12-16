package com.darshan09200.restaurantorderingsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

enum DataType {
    ALL,
    CART
}

enum Action {
    ADD_TO_CART,
    INCREASE_COUNTER,
    DECREASE_COUNTER
}

interface CartUpdateListener {
    void recalculate();
}

public class MealsAdapter extends ArrayAdapter<Meal> {
    private final LayoutInflater layoutInflater;
    private final int layoutResource;
    private final DataType type;

    private final List<Meal> meals;
    private CartUpdateListener listener;

    public MealsAdapter(@NonNull Context context, int resource, DataType type) {
        super(context, resource);

        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.type = type;
        if (type == DataType.ALL)
            this.meals = Restaurant.getInstance().getMeals();
        else
            this.meals = Restaurant.getInstance().getCartItems();

        if (context instanceof CartUpdateListener) {
            this.listener = (CartUpdateListener) context;
        }
    }

    @Nullable
    @Override
    public Meal getItem(int position) {
        return meals.get(position);
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

        Meal item = meals.get(position);
        ImageView mealImage = v.findViewById(R.id.mealImage);
        TextView mealTitle = v.findViewById(R.id.mealTitle);
        TextView mealPrice = v.findViewById(R.id.mealPrice);
        TextView mealQuantity = v.findViewById(R.id.mealQuantity);

        Button addToCartButton = v.findViewById(R.id.addToCart);
        LinearLayout counterLayout = v.findViewById(R.id.counterButton);

        ImageButton addCounter = v.findViewById(R.id.counterAdd);
        ImageButton subtractCounter = v.findViewById(R.id.counterSubtract);
        TextView quantityLabel = v.findViewById(R.id.counterQty);

        addToCartButton.setOnClickListener(view -> onButtonClick(Action.ADD_TO_CART, item.getId()));

        mealTitle.setText(item.getName());
        mealImage.setImageResource(item.getImageId());
        mealPrice.setText(String.format("Price: $%.2f", item.getPrice()));
        if (type == DataType.CART) {
            mealQuantity.setVisibility(View.GONE);
        } else {
            mealQuantity.setText(String.format("Quantity: %d", item.getQuantity()));
        }

        if (item.isAddedToCart()) {
            if (addToCartButton.getVisibility() == View.VISIBLE) {
                addToCartButton.setVisibility(View.INVISIBLE);
                counterLayout.setVisibility(View.VISIBLE);
            }
            quantityLabel.setText(String.valueOf(item.getCartQuantity()));
            addCounter.setOnClickListener(view -> onButtonClick(Action.INCREASE_COUNTER, item.getId()));

            if (item.getCartQuantity() >= item.getQuantity() && addCounter.isEnabled()) {
                addCounter.setAlpha(0.5f);
                addCounter.setEnabled(false);
            } else if (!addCounter.isEnabled()) {
                addCounter.setAlpha(1.0f);
                addCounter.setEnabled(true);
            }

            if (item.getCartQuantity() <= 1) {
                subtractCounter.setOnClickListener(view -> showRemoveFromCartDialog(item.getId()));
            } else {
                subtractCounter.setOnClickListener(view -> onButtonClick(Action.DECREASE_COUNTER, item.getId()));
            }
        } else if (addToCartButton.getVisibility() == View.INVISIBLE) {
            quantityLabel.setText("0");
            addToCartButton.setVisibility(View.VISIBLE);
            counterLayout.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    private void onButtonClick(Action action, String id) {
        Restaurant.getInstance().updateQuantity(action, id);

        if (type == DataType.CART) {
            meals.clear();
            meals.addAll(Restaurant.getInstance().getCartItems());

            if (listener != null) listener.recalculate();
        }

        notifyDataSetChanged();
    }

    private void showRemoveFromCartDialog(String id) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete item")
                .setMessage("Are you sure you want to remove this item from cart?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onButtonClick(Action.DECREASE_COUNTER, id);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
