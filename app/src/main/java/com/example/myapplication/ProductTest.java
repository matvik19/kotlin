package com.example.myapplication;

import com.example.myapplication.domain.Clothing;
import com.example.myapplication.domain.Electronics;
import com.example.myapplication.domain.Food;
import com.example.myapplication.domain.LuxuryProduct;
import com.example.myapplication.domain.Product;

public class ProductTest {
    public static void main(String[] args) {
        // Создание объектов
        Electronics tv = new Electronics(101, "Smart TV", 999.99, 24);
        Clothing tshirt = new Clothing(202, "T-Shirt", 19.99, "L");
        Food apple = new Food(303, "Apple", 0.99, "2024-01-01");
        LuxuryProduct watch = new LuxuryProduct(404, "Luxury Watch", 5000, 1000);

        // Отображение информации
        printProduct(tv);
        printProduct(tshirt);
        printProduct(apple);
        printProduct(watch);
    }

    // Метод для отображения данных о товаре
    private static void printProduct(Product product) {
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Product Price: $" + product.getPrice());

        if (product instanceof Electronics) {
            System.out.println("Warranty Period: " + ((Electronics) product).getWarrantyPeriod() + " months");
        }

        if (product instanceof Clothing) {
            System.out.println("Size: " + ((Clothing) product).getSize());
        }

        if (product instanceof Food) {
            System.out.println("Expiration Date: " + ((Food) product).getExpirationDate());
        }

        if (product instanceof LuxuryProduct) {
            System.out.println("Luxury Tax: $" + ((LuxuryProduct) product).getLuxuryTax());
            System.out.println("Price with Tax: $" + ((LuxuryProduct) product).getPriceWithTax());
        }
        System.out.println();
    }
}
