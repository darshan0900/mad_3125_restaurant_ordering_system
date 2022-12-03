package com.darshan09200.restaurantorderingsystem;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static Restaurant restaurantInstance;
    private List<Meal> meals;
    private TextView badgeCount;

    private Restaurant() {
        meals = new ArrayList<>();
        meals.add(new Meal(R.drawable.vada_pav, "Vada Pav", 6.99, 10));
        meals.add(new Meal(R.drawable.chaat, "Indian Chaat", 3.99, 13));
        meals.add(new Meal(R.drawable.dal_makhani, "Dal Makhani", 3.99, 13));
        meals.add(new Meal(R.drawable.dhokla, "Dhokla", 3.99, 13));
        meals.add(new Meal(R.drawable.masala_dosa, "Masala Dosa", 3.99, 13));
        meals.add(new Meal(R.drawable.pani_puri, "Pani Puri", 3.99, 13));
        meals.add(new Meal(R.drawable.idli, "Idli", 3.99, 13));
        meals.add(new Meal(R.drawable.barfi, "Barfi", 3.99, 13));
        meals.add(new Meal(R.drawable.masala_chai, "Masala Chai", 3.99, 13));
        meals.add(new Meal(R.drawable.stuffed_paratha, "Stuffed Paratha", 3.99, 13));
    }

    public static Restaurant getInstance() {
        if (restaurantInstance == null) {
            restaurantInstance = new Restaurant();
        }
        return restaurantInstance;
    }

    public Meal getMeal(String id) {
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            if (meal.getId().equals(id)) return meal;
        }
        return null;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public List<Meal> getCartItems() {
        List<Meal> cartItems = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            Meal item = meals.get(i);
            if (item.isAddedToCart()) cartItems.add(item);
        }
        return cartItems;
    }

    public void updateQuantity(Action action, String id) {
        Meal item = getMeal(id);
        if (item != null) {
            int quantity = item.getCartQuantity();
            if (action == Action.ADD_TO_CART) quantity = 1;
            else if (action == Action.INCREASE_COUNTER) quantity++;
            else if (action == Action.DECREASE_COUNTER) quantity--;
            if (quantity <= 0) {
                quantity = 0;
            }
            if (quantity <= item.getQuantity()) {
                item.setCartQuantity(quantity);
            }
        }
        updateBadge();
    }

    public void registerBadge(TextView badgeCount) {
        this.badgeCount = badgeCount;
    }

    public void unregisterBadge() {
        this.badgeCount = null;
    }

    public void updateBadge() {
        if (badgeCount != null) {
            int cartItemsCount = getCartItems().size();
            if (cartItemsCount > 0) {
                if (badgeCount.getVisibility() == View.INVISIBLE)
                    badgeCount.setVisibility(View.VISIBLE);
                badgeCount.setText(String.valueOf(cartItemsCount));
            } else if (badgeCount.getVisibility() == View.VISIBLE) {
                badgeCount.setVisibility(View.INVISIBLE);
            }
        }
    }

}
