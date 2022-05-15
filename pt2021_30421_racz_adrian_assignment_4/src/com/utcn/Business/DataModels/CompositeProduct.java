package com.utcn.Business.DataModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for composite products.
 * Using setters for anything but the title on this one is not recommended.
 *
 */
public class CompositeProduct extends MenuItem {
    private final List<MenuItem> products;

    public CompositeProduct() {
        super();
        this.products = new ArrayList<>();
    }

    public CompositeProduct(String title, List<MenuItem> products) {
        this.setTitle(title);
        this.products = products;
        update();
    }

    public List<MenuItem> getProducts() {
        return products;
    }

    /**
     * Adds a MenuItem to the internal list, then updates the base attributes
     *
     * @param product   A MenuItem to be added
     */
    public void addProduct(MenuItem product) {
        products.add(product);
        update();
    }

    /**
     * Removes a MenuItem from the internal list, then updates the base attributes
     *
     * @param product   A MenuItem to be removed
     */
    public void removeProduct(MenuItem product) {
        products.remove(product);
        update();
    }

    /**
     * Recomputes the base attributes by adding them from the internal product list
     */
    private void update() {
        Double totalRating = 0.; // Not sure if I should compute the average of the ratings, but whatever
        Integer totalCalories = 0, totalProteins = 0, totalFats = 0, totalSodium = 0, totalPrice = 0;
        for (MenuItem product : products) {
            totalRating += product.getRating();
            totalCalories += product.getCalories();
            totalProteins += product.getProtein();
            totalFats += product.getFat();
            totalSodium += product.getSodium();
            totalPrice += product.getPrice();
        }
        if (products.size() == 0)
            setRating(0.);
        else
            setRating(totalRating / products.size());
        setCalories(totalCalories);
        setProtein(totalProteins);
        setFat(totalFats);
        setSodium(totalSodium);
        setPrice(totalPrice);
    }

    /**
     * Computes the price of the product.
     *
     * @return  The price, an Integer.
     */
    @Override
    public Integer computePrice() {
        Integer finalPrice = 0;
        for (MenuItem product : products) {
            finalPrice += product.getPrice();
        }
        return finalPrice;
    }

    /**
     * A static method that transforms a CompositeProducts' ingredients names in a string, separated by ","
     *
     * @param menuItem  The MenuItem to be decomposed
     * @return          CompositeProducts' ingredients names in a string, separated by ",", "EMPTY" if it has
     *                  no products or "" if it is not a CompositeProduct
     */
    public static String decomposeProduct(MenuItem menuItem) {
        if (menuItem instanceof CompositeProduct) {
            if (((CompositeProduct) menuItem).getProducts().isEmpty()) {
                return "EMPTY";
            }
            StringBuilder components = new StringBuilder();
            for (MenuItem product : ((CompositeProduct) menuItem).getProducts()) {
                components.append(product.getTitle()).append(", ");
            }
            return components.substring(0, components.toString().length() - 2);
        }
        return "";
    }
}
