package com.shop.services;

import com.shop.domain.Product;
import com.shop.errors.ShopException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
    private List<Product> products;

    @Override
    public void loadProducts(String pathToResource) throws ShopException {
        products = retrieveValuesFromCSVFile(pathToResource);
    }

    @Override
    public void listProducts() throws ShopException {
        if (products == null)
            throw new ShopException("there are no products to list");

        for (Product p : products) {
            System.out.println(p.toString());
        }
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Return an array with values from specified csv file.
     * Exception will be thrown if no csv file was found or there was a naming issue
     * @param pathToResource the path to csv file (example: /myfile.csv)
     * @return list containing Product objects
     * @throws ShopException
     */
    private List<Product> retrieveValuesFromCSVFile(String pathToResource) throws ShopException {
        List<Product> products = new ArrayList();
        Path path = null;
        URL resource = ProductServiceImpl.class.getResource(pathToResource);

        try {
            path = Paths.get(resource.toURI());
            log.debug("debug: path_to_csv=" + path.toString());
            products = Files.lines(Paths.get(path.toString()))
                    .skip(1) //skip 1 row with headers
                    .map(mapToItem)
                    .collect(Collectors.toList());
            log.debug("debug: finished stream operations. created list size=" + products.size());

        } catch (URISyntaxException e) {
            throw new ShopException("uri syntax exception");
        }  catch (IOException e) {
            throw new ShopException("no such csv file");
        } catch (NullPointerException e) {
            throw new ShopException("no such csv file");
        }

        return products;
    }

    /**
     * Function to map values from one row of csv file (in String format) to Product object.
     */
    private Function<String, Product> mapToItem = (line) -> {
        String[] row = line.split(",");
        Product product = new Product();
        product.setId(row[0]);//<-- the first column in the csv file
        if (row[1] != null && row[1].trim().length() > 0) {
            product.setName(row[1]);
        }
        if (row[2] != null && row[2].trim().length() > 0) {
            product.setPrice(row[2]);
        }
        return product;
    };
}
