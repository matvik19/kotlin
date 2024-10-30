package com.example.myapplication.domain;

public class Food extends Product {
    private String expirationDate;

    public Food(int productId, String name, double price, String expirationDate) {
        super(productId, name, price);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}