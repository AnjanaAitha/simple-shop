package com.shop.services;

import com.shop.errors.ShopException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    ProductService productService;

    @BeforeEach
    void createShopService() {
        productService = new ProductServiceImpl();
    }

    @AfterEach
    void resetProductsInShop() {
        productService = null;
    }

    @Test
    void loadProductsFromExistingFile() {
        assertNull(productService.getProducts());
        assertAll(
                () -> productService.loadProducts("/product-data.csv")
        );

        assertEquals(7, productService.getProducts().size(), "Size of list of Products should be correct");
    }

    @Test
    void loadProductsFromNonExistingFile() {
        ShopException shopException = assertThrows(ShopException.class, () -> productService.loadProducts("/none.csv"));
        assertEquals("no such csv file", shopException.getDetailMessage());
    }

    @Test
    void listProductsBeforeAndAfterLoading() {
        ShopException shopException = assertThrows(ShopException.class, () -> productService.listProducts());
        assertEquals("there are no products to list", shopException.getDetailMessage());

        assertAll(
                () -> productService.loadProducts("/product-data.csv")
        );

        assertNotNull(productService.getProducts(), "List of products should not be null");
        assertEquals(7, productService.getProducts().size(), "List should have correct size");
    }
}