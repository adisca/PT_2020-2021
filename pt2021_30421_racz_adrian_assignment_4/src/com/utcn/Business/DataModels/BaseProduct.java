package com.utcn.Business.DataModels;

import com.utcn.Util.WrongSizeOfCollectionException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for base products.
 */
public class BaseProduct extends MenuItem {

    public BaseProduct(String title, Double rating, Integer calories, Integer protein, Integer fat, Integer sodium,
                       Integer price) {
        super(title, rating, calories, protein, fat, sodium, price);
    }

    /**
     * A constructor that parses a string
     *
     * @param str   A string containing the attributes separated by ","
     * @throws WrongSizeOfCollectionException   The string does not have the right number of attributes
     */
    public BaseProduct(String str) throws WrongSizeOfCollectionException {
        List<String> attributes = Arrays.stream(str.split(",")).collect(Collectors.toList());
        if (attributes.size() != 7)
            throw new WrongSizeOfCollectionException();
        int i = 0;
        setTitle(attributes.get(i++));
        setRating(Double.valueOf(attributes.get(i++)));
        setCalories(Integer.valueOf(attributes.get(i++)));
        setProtein(Integer.valueOf(attributes.get(i++)));
        setFat(Integer.valueOf(attributes.get(i++)));
        setSodium(Integer.valueOf(attributes.get(i++)));
        setPrice(Integer.valueOf(attributes.get(i)));
    }

    /**
     * Calculates the price
     *
     * @return  The price
     */
    @Override
    public Integer computePrice() {
        return getPrice();
    }
}
