package com.utcn.Business;

import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Util.ExistentUniqueFieldException;
import com.utcn.Util.ObjectNotFoundException;
import com.utcn.Util.WrongSizeOfCollectionException;

import java.io.IOException;
import java.util.List;

/**
 * Interface for DeliveryServiceProcessing.
 * Contains all its methods.
 * I was supposed to implement Design by Contract, however I didn't manage to do it
 */
public interface IDeliveryServiceProcessing {

    /**
     * Force serialization of internal values
     */
    void save();

    /**
     * Imports the MenuItems from a file
     *
     * @param filePath  The path of the file
     * @throws IOException                      Error when working with a file
     * @throws WrongSizeOfCollectionException   The file has an improper data format
     */
    void importCSV(String filePath) throws IOException, WrongSizeOfCollectionException;

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
    void createBaseProduct(String title, Double rating, Integer calories, Integer protein, Integer fat,
                           Integer sodium, Integer price) throws ExistentUniqueFieldException;

    /**
     * Removes a product from the list
     *
     * @param toRemove  The name of the product to be removed
     * @throws ObjectNotFoundException  The searched object has not been found
     */
    void removeProduct(String toRemove) throws ObjectNotFoundException;

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
    void modifyProduct(String toModify, String newTitle, Double newRating, Integer newCalories,
                       Integer newProtein, Integer newFat, Integer newSodium, Integer newPrice)
                                throws ObjectNotFoundException, ExistentUniqueFieldException;

    /**
     * Creates a composite product
     *
     * @param title     The name
     * @param menuItems The ingredients
     * @throws ExistentUniqueFieldException  A unique field (name) already contains the value
     */
    void createCompositeProduct(String title, List<MenuItem> menuItems) throws ExistentUniqueFieldException;

    /**
     * Creates an order and adds it to the hashTable
     *
     * @param clientID  The id of the client
     * @param menuItems The products ordered
     * @return          The total price
     */
    Integer createOrder(Integer clientID, List<MenuItem> menuItems);

    /**
     * Searches for a MenuItem in the menu with the given name
     *
     * @param name  The name to search for
     * @return      The MenuItem if found, else null
     */
    MenuItem searchByName(String name);

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
    List<MenuItem> searchProduct(String titleLike, Double ratingMin, Double ratingMax, Integer caloriesMin,
                                 Integer caloriesMax, Integer proteinMin, Integer proteinMax, Integer fatMin,
                                 Integer fatMax, Integer sodiumMin, Integer sodiumMax, Integer priceMin,
                                 Integer priceMax);
}
