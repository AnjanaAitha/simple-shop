package com.shop.main;

import com.shop.services.BasketService;
import com.shop.services.BasketServiceImpl;
import com.shop.services.ProductService;
import com.shop.services.ProductServiceImpl;
import com.shop.shop.Shop;

import java.util.Scanner;

/**
 * Entry to the Shopping Application.
 */
public class Application {

    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String LIST_COMMAND = "list";
    private static final String TOTAL_COMMAND = "total";
    private static final String QUIT_COMMAND = "Q";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************************************");
        System.out.println("* Welcome to the My Online Shop *");
        System.out.println("************************************");
        System.out.println("Enter \"Q\" to Quit");
        System.out.println("Enter \"add <ProductId>\" to add to basket");
        System.out.println("Enter \"remove <ProductId>\" to remove from basket");
        System.out.println("Enter \"list\" to show a list of products in the inventory");
        System.out.println("Enter \"total\" to show the total price of the basket");

        Shop shop = new Shop();
        ProductService productService = new ProductServiceImpl();
        shop.setProductService(productService);
        shop.loadProducts();

        BasketService basketService = new BasketServiceImpl(productService.getProducts());
        shop.setBasketService(basketService);

        while (true) {
            String inputValue = scanner.nextLine();

            if (inputValue.startsWith(ADD_COMMAND)) {

                String itemId = inputValue.substring(ADD_COMMAND.length()).trim();
                shop.addProductToBasket(itemId);

            } else if (inputValue.startsWith(REMOVE_COMMAND)) {

                String itemId = inputValue.substring(REMOVE_COMMAND.length()).trim();
                shop.removeProductFromBasket(itemId);

            } else if (inputValue.startsWith(LIST_COMMAND)) {
                shop.listProducts();

            } else if (inputValue.startsWith(TOTAL_COMMAND)) {
                shop.getTotal();

            } else if (QUIT_COMMAND.equalsIgnoreCase(inputValue)) {
                System.out.println("Thanks for shopping with us!");
                System.exit(0);
            }
        }
    }
}
