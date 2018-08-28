package com.shop.services;

import com.shop.domain.Product;
import com.shop.errors.ShopException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BasketServiceImpl implements BasketService {

    private static final Logger log = Logger.getLogger(BasketServiceImpl.class);
    private List<Product> availableProducts;
    private List<Product> productsInBasket;

    public BasketServiceImpl(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
        productsInBasket = new ArrayList();
    }

    @Override
    public void addProductToBasket(String productId) throws ShopException {
        Product productToAdd = null;
        for (Product p : availableProducts) {
            if (p.getId().equalsIgnoreCase(productId)) {
                productToAdd = p;
                break;
            }
        }

        if (productToAdd == null) {
            if (productId.equals(""))
                throw new ShopException("id of a product is not specified");

            throw new ShopException(String.format("product with id=%s does not exist", productId));
        }
        else {
            productsInBasket.add(productToAdd);
            log.debug("debug: product added to the basket");
        }
    }

    @Override
    public void removeProductFromBasket(String productId) throws ShopException {
        for (Product p : productsInBasket) {
            if (p.getId().equalsIgnoreCase(productId)) {
                productsInBasket.remove(p);
                log.debug("debug: product removed from the basket");
                return;
            }
        }

        if (productId.equals(""))
            throw new ShopException("id of a product is not specified");

        throw new ShopException(String.format("product with id=%s was not found in the basket", productId));
    }

    @Override
    public String getTotalValueOfBasket() {
        log.debug("debug: getTotal called");

        double sum = productsInBasket.stream()
                .mapToDouble(product -> Double.valueOf(product.getPrice().substring(1))) //substring gets rid of £
                .sum();

        log.debug("debug: calculated sum="+sum);
        log.debug("debug: returned sum="+String.format("%.2f", sum));
        return "£" + String.format("%.2f", sum);
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public List<Product> getProductsInBasket() {
        return productsInBasket;
    }
}
