package com.crudjsp.crudjsp.model;
import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private int productId;
    private String productName;
    private double price;
    private String codeProduct;
    private Category category;
    private Brand brand;
    private int stockQuantity;
    private char active;
}
