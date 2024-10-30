package com.example.myapplication.domain;

public class LuxuryProduct extends Product {
    private double luxuryTax;

    public LuxuryProduct(int productId, String name, double price, double luxuryTax) {
        super(productId, name, price);
        this.luxuryTax = luxuryTax;
    }

    public double getLuxuryTax() {
        return luxuryTax;
    }

    public double getPriceWithTax() {
        return price + luxuryTax;
    }
}