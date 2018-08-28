package com.shop.services;

import com.shop.domain.Product;
import com.shop.errors.ShopException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceImplTest {


    /** create list with 3 available products for purchasing */
    private List<Product> createAvailableProducts() {
        List<Product> products = new ArrayList<>(3);
        Product p1 = new Product("1", "dress", "£120.98");
        Product p2 = new Product("2", "jeans", "£89.99");
        Product p3 = new Product("3", "mystery item", "£100");
        products.add(p1);
        products.add(p2);
        products.add(p3);

        return products;
    }

    @Test
    void addExistingProductToBasket() {
        BasketService basket = new BasketServiceImpl(createAvailableProducts());
        int totalItemsAvailable = ((BasketServiceImpl) basket).getAvailableProducts().size();
        int itemsInBasket = ((BasketServiceImpl) basket).getProductsInBasket().size();

        //assert included statements do not throw exceptions
        assertAll(
                () -> basket.addProductToBasket("1")
        );

        assertEquals(totalItemsAvailable, ((BasketServiceImpl) basket).getAvailableProducts().size(), "Amount of available products in the shop should not change");
        assertEquals(itemsInBasket+1, ((BasketServiceImpl) basket).getProductsInBasket().size(), "Amount of items in basket should increase by 1");

        //check if the id of item in the basket is correct
        Product product = ((BasketServiceImpl) basket).getProductsInBasket().get(0);
        assertEquals("1", product.getId(), "Product ID in the basket should be correct");
    }

    @Test
    void addNonExistingProductToBasket() {
        BasketService basket = new BasketServiceImpl(createAvailableProducts());

        ShopException shopException = assertThrows(ShopException.class, () -> basket.addProductToBasket("9"));
        assertEquals("product with id=9 does not exist", shopException.getDetailMessage());

        ShopException shopExceptionEmptyValue = assertThrows(ShopException.class, () -> basket.addProductToBasket(""));
        assertEquals("id of a product is not specified", shopExceptionEmptyValue.getDetailMessage());
    }

    @Test
    void removeProductFromBasket() {
        BasketService basket = new BasketServiceImpl(createAvailableProducts());
        int totalItemsAvailable = ((BasketServiceImpl) basket).getAvailableProducts().size();
        int itemsInBasket = ((BasketServiceImpl) basket).getProductsInBasket().size();

        assertAll(
                () -> basket.addProductToBasket("1"),
                () -> basket.removeProductFromBasket("1")
        );

        assertEquals(itemsInBasket, ((BasketServiceImpl) basket).getProductsInBasket().size(), "Amount after removing should be equal to amount before adding a product to the basket");
        assertEquals(totalItemsAvailable, ((BasketServiceImpl) basket).getAvailableProducts().size(), "Amount of available products in the shop should not change");

        //try to remove same item again
        ShopException shopException = assertThrows(ShopException.class, () -> basket.removeProductFromBasket("1"));
        assertEquals("product with id=1 was not found in the basket", shopException.getDetailMessage());

        //try to remove not specified item
        ShopException shopExceptionEmptyValue = assertThrows(ShopException.class, () -> basket.removeProductFromBasket(""));
        assertEquals("id of a product is not specified", shopExceptionEmptyValue.getDetailMessage());
    }

    @Test
    void checkTotalAfterAddingAndRemovingProducts() {
        BasketService basket = new BasketServiceImpl(createAvailableProducts());

        assertAll(
                () -> basket.addProductToBasket("1"),
                () -> basket.addProductToBasket("3")
        );
        assertEquals("£220.98", basket.getTotalValueOfBasket());
    }
}