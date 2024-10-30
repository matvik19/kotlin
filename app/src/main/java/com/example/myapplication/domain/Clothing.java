package com.example.myapplication.domain;

public class Clothing extends Product {
    private String size;

    public Clothing(int productId, String name, double price, String size) {
        super(productId, name, price);
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}