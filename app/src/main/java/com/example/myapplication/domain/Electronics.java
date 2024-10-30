package com.example.myapplication.domain;

public class Electronics extends Product {
    private int warrantyPeriod; // гарантийный срок в месяцах

    public Electronics(int productId, String name, double price, int warrantyPeriod) {
        super(productId, name, price);
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
}