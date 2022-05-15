package com.utcn.Business;

import com.utcn.Business.DataModels.*;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Data.Serialization;
import com.utcn.Util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private static final String menuSaveFile = "menu.ser";
    private static final String ordersSaveFile = "orders.ser";

    private final Map<Order, Collection<MenuItem>> orders;
    private final Collection<MenuItem> menu;

    public DeliveryService() {
        menu = Serialization.deserializeMenu(menuSaveFile);
        orders = Serialization.deserializeHashMap(ordersSaveFile);
    }

    public Map<Order, Collection<MenuItem>> getOrders() {
        return orders;
    }

    public Collection<MenuItem> getMenu() {
        return menu;
    }

    /**
     * Force serialization of internal values
     */
    @Override
    public void save() {
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
        Serialization.serialize(ordersSaveFile, (HashMap<Order, Collection<MenuItem>>) orders);
    }

    /**
     * Imports the MenuItems from a file
     *
     * @param filePath  The path of the file
     * @throws IOException                      Error when working with a file
     * @throws WrongSizeOfCollectionException   The file has an improper data format
     */
    @Override
    public void importCSV(String filePath) throws IOException, WrongSizeOfCollectionException {
        menu.clear();
        Files.lines(Paths.get(filePath)).skip(1).map(BaseProduct::new).
                collect(Collectors.groupingBy(BaseProduct::getTitle)).values().
                forEach(baseProducts -> menu.add(baseProducts.get(0)));
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
    }

    /**
     * Creates a composite product
     *
     * @param title     The name
     * @param menuItems The ingredients
     * @throws ExistentUniqueFieldException  A unique field (name) already contains the value
     */
    @Override
    public void createCompositeProduct(String title, List<MenuItem> menuItems) throws ExistentUniqueFieldException {
        menu.forEach(menuItem -> {
            if (menuItem.getTitle().equals(title))
                throw new ExistentUniqueFieldException();
        });
        menu.add(new CompositeProduct(title, menuItems));
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
    }

    /**
     * Creates a base product and serializes the list
     *
     * @param title     A name
     * @param rating    A rating
     * @param calories  A nb of calories
     * @param protein   A nb of proteins
     * @param fat       A nb of fats
     * @param sodium    A nb of sodium
     * @param price     A price
     * @throws ExistentUniqueFieldException A unique field (name) already contains the value
     */
    @Override
    public void createBaseProduct(String title, Double rating, Integer calories, Integer protein, Integer fat,
                                  Integer sodium, Integer price)
            throws ExistentUniqueFieldException, ValueOutOfBoundsException {
        if (rating < 0. || rating > 5. || calories < 0 || protein < 0 || fat < 0 || sodium < 0 || price < 0)
            throw new ValueOutOfBoundsException();
        menu.forEach(menuItem -> {
            if (menuItem.getTitle().equals(title))
                throw new ExistentUniqueFieldException();
        });
        menu.add(new BaseProduct(title, rating, calories, protein, fat, sodium, price));
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
    }

    /**
     * Removes a product from the list
     *
     * @param toRemove  The name of the product to be removed
     * @throws ObjectNotFoundException  The searched object has not been found
     */
    @Override
    public void removeProduct(String toRemove) throws ObjectNotFoundException {
        if(menu.stream().filter(menuItem -> menuItem.getTitle().equals(toRemove)).count() < 1)
            throw new ObjectNotFoundException();
        menu.removeIf(menuItem -> menuItem.getTitle().equals(toRemove));
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
    }

    /**
     * Modifies a base product
     *
     * @param toModify      The name of the product to be modified
     * @param newTitle      A new name
     * @param newRating     A new rating
     * @param newCalories   A new nb of calories
     * @param newProtein    A new nb of proteins
     * @param newFat        A new nb of fats
     * @param newSodium     A new nb of sodium
     * @param newPrice      A new price
     * @throws ObjectNotFoundException       The searched object has not been found
     * @throws ExistentUniqueFieldException  A unique field (name) already contains the value
     */
    @Override
    public void modifyProduct(String toModify, String newTitle, Double newRating, Integer newCalories,
                              Integer newProtein, Integer newFat, Integer newSodium, Integer newPrice)
                                throws ObjectNotFoundException, ExistentUniqueFieldException, WrongClassException {
        if (menu.stream().filter(menuItem -> menuItem.getTitle().equals(toModify)).count() < 1)
            throw new ObjectNotFoundException();
        if (!(menu.stream().filter(menuItem -> menuItem.getTitle().equals(toModify)).collect(Collectors.toList()).get(0)
                instanceof BaseProduct))
            throw new WrongClassException();
        menu.forEach(menuItem -> {
            if (menuItem.getTitle().equals(newTitle))
                throw new ExistentUniqueFieldException();
        });
        menu.forEach(menuItem -> {
            if (menuItem.getTitle().equals(toModify)) {
                if (!newTitle.equals(""))
                    menuItem.setTitle(newTitle);
                if (newRating >= 0.)
                    menuItem.setRating(newRating);
                if (newPrice >= 0)
                    menuItem.setPrice(newPrice);
                if (newCalories >= 0)
                    menuItem.setCalories(newCalories);
                if (newProtein >= 0)
                    menuItem.setProtein(newProtein);
                if (newFat >= 0)
                    menuItem.setFat(newFat);
                if (newSodium >= 0)
                    menuItem.setSodium(newSodium);
            }
        });
        Serialization.serialize(menuSaveFile, (ArrayList<MenuItem>) menu);
    }

    /**
     * Creates an order and adds it to the hashTable
     *
     * @param clientID  The id of the client
     * @param menuItems The products ordered
     * @return          The total price
     */
    @Override
    public Integer createOrder(Integer clientID, List<MenuItem> menuItems) {
        Integer cost = 0;
        Order order = new Order(orders.size(), clientID, LocalDateTime.now());

        orders.put(order, menuItems);
        Serialization.serialize(ordersSaveFile, (HashMap<Order, Collection<MenuItem>>) orders);
        setChanged();
        notifyObservers(order);
        for (MenuItem menuItem : menuItems) {
            cost += menuItem.getPrice();
        }
        return cost;
    }

    /**
     * Searches for a MenuItem in the menu with the given name
     *
     * @param name  The name to search for
     * @return      The MenuItem if found, else null
     */
    @Override
    public MenuItem searchByName(String name) {
        return menu.stream().filter(menuItem -> menuItem.getTitle().equals(name)).findFirst().orElse(null);
    }

    /**
     * Searches for all products that respect the given condittions.
     * A condition not specified will be taken as true.
     *
     * @param titleLike     A substring of the name
     * @param ratingMin     A min for rating
     * @param ratingMax     A max for rating
     * @param caloriesMin   A min for calories
     * @param caloriesMax   A max for calories
     * @param proteinMin    A min for protein
     * @param proteinMax    A max for protein
     * @param fatMin        A min for fat
     * @param fatMax        A max for fat
     * @param sodiumMin     A min for sodium
     * @param sodiumMax     A max for sodium
     * @param priceMin      A min for price
     * @param priceMax      A max for price
     * @return              A list of products that respect the condittions
     */
    @Override
    public List<MenuItem> searchProduct(String titleLike, Double ratingMin, Double ratingMax, Integer caloriesMin,
                                        Integer caloriesMax, Integer proteinMin, Integer proteinMax, Integer fatMin,
                                        Integer fatMax, Integer sodiumMin, Integer sodiumMax, Integer priceMin,
                                        Integer priceMax) {
        return menu.stream().filter(menuItem ->
                (menuItem.getTitle().toUpperCase().contains(titleLike.toUpperCase()) ||
                        menuItem.getTitle().isBlank()) &&
                menuItem.getRating() >= ratingMin && menuItem.getRating() <= ratingMax &&
                menuItem.getCalories() >= caloriesMin && menuItem.getCalories() <= caloriesMax &&
                menuItem.getProtein() >= proteinMin && menuItem.getProtein() <= proteinMax &&
                menuItem.getFat() >= fatMin && menuItem.getFat() <= fatMax &&
                menuItem.getSodium() >= sodiumMin && menuItem.getSodium() <= sodiumMax &&
                menuItem.getPrice() >= priceMin && menuItem.getPrice() <= priceMax).
                collect(Collectors.toList());
    }

}
