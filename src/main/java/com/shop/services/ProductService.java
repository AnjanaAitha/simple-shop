package com.shop.services;

import com.shop.domain.Product;
import com.shop.errors.ShopException;

import java.util.List;

/**
 * Provides Products/Inventory-related functionality for (Online) Shop.
 */
public interface ProductService {

    /**
     * Parse csv file from specified location and map values from each row to Product object.
     * Structure of csv row: #productId,#productName,#price
     * @throws ShopException is thrown if csv file does not exist or the name of it is invalid
     */
    void loadProducts(String pathToResource) throws ShopException;

    /**
     * List products currently available to be purchased. Products must be loaded before listing.
     * @throws ShopException if the list of products does not exist.
     */
    void listProducts() throws ShopException;

    /**
     * Get products currently available to be purchased
     * @return list containing Product objects
     */
    List<Product> getProducts();
}
