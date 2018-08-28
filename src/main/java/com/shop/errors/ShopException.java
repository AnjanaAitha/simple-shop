package com.shop.errors;

/**
 * The general class representing all possible exceptions thrown by Shop.
 * Could be extended if needed.
 */
public class ShopException extends Exception {

    //TODO: can add additional data if needed
    //private String message;

    public ShopException(String message) {
        super(message);
    }

    public String getDetailMessage() {
        return super.getMessage();
    }
}
