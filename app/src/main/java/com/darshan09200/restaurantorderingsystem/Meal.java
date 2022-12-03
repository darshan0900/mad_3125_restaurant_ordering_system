package com.darshan09200.restaurantorderingsystem;

import androidx.annotation.DrawableRes;

public class Meal {
    private static int MEAL_COUNT = 0;
    private final String id;
    @DrawableRes
    private final int imageId;
    private final String name;
    private final double price;
    private final int quantity;
    private int cartQuantity = 0;

    public Meal(@DrawableRes int imageId, String name, double price, int quantity) {
        this.id = "meal_" + ++MEAL_COUNT;
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public boolean isAddedToCart() {
        return cartQuantity > 0;
    }
}
