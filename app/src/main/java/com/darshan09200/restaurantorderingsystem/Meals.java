package com.darshan09200.restaurantorderingsystem;

import java.util.ArrayList;
import java.util.List;

public class Meals {
    private static Meals mealsInstance;
    private List<Meal> meals;
    private List<CartItem> cartItems;

    private Meals() {
        meals = new ArrayList<>();
        meals.add(new Meal());
        meals.add(new Meal());
    }

    public static Meals getInstance() {
        if (mealsInstance == null) {
            mealsInstance = new Meals();
        }
        return mealsInstance;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(int id){
        updateQuantity(id, 1);
    }

    public void updateQuantity(int id, int quantity){

    }
}
