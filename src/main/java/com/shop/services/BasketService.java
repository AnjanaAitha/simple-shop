package com.shop.services;

import com.shop.errors.ShopException;

/**
 * Provides basket-related functionality for (Online) shop.
 */
public interface BasketService {

    /**
     * Add product with specified id to the basket
     * @throws ShopException if id is not specified or product with specified id does not exist
     */
    void addProductToBasket(String productId) throws ShopException;

    /**
     * Remove product with specified id from the basket
     * @throws ShopException if id is not specified or product with specified id was not found in the basket
     */
    void removeProductFromBasket(String productId) throws ShopException;

    /**
     * Get the total price of all items currently in the basket
     * @return String value (with currency sign) representing total price.
     */
    String getTotalValueOfBasket();
}
