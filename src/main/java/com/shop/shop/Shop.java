package com.shop.shop;

import com.shop.errors.ShopException;
import com.shop.services.BasketService;
import com.shop.services.ProductService;

public class Shop {

    ProductService productService;
    BasketService basketService;

    public Shop() {
    }

    public void loadProducts() {
        // TODO Exercise 1a - Parsing the product-data.csv data file
        try {
            productService.loadProducts("/product-data.csv");
        } catch (ShopException e) {
            System.out.println(e.getDetailMessage());
        }
    }

    public void listProducts() {
        // TODO Exercise 1b - Listing products
        try {
            productService.listProducts();
        } catch (ShopException e) {
            System.out.println(e.getDetailMessage());
        }
    }

    /**
     * Add a product to the Basket
     */
    public void addProductToBasket(String productId) {
        // TODO Exercise 2a - feature to add Products to the basket
        try {
            basketService.addProductToBasket(productId);
        } catch (ShopException e) {
            System.out.println(e.getDetailMessage());
        }
    }

    /**
     * Remove a product from the Basket
     */
    public void removeProductFromBasket(String productId) {
        // TODO Exercise 2b - feature to remove Products from the basket
        try {
            basketService.removeProductFromBasket(productId);
        } catch (ShopException e) {
            System.out.println(e.getDetailMessage());
        }
    }

    /**
     * Return the total value of the products in the basket
     */
    public void getTotal() {
        // TODO Exercise 2c - feature to show the total value of Products in the basket
        System.out.println("Total: " + basketService.getTotalValueOfBasket());
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }
}
