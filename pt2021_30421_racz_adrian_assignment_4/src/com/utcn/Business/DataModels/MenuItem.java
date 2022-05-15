package com.utcn.Business.DataModels;

import java.io.Serializable;

/**
 * Abstract Class for the products to be ordered
 */
public abstract class MenuItem implements Serializable {
    private String title;
    private Double rating;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer sodium;
    private Integer price;

    public MenuItem() {
        this.title = "untitled";
        this.rating = 0.;
        this.calories = 0;
        this.protein = 0;
        this.fat = 0;
        this.sodium = 0;
        this.price = 0;
    }

    public MenuItem(String title, Double rating, Integer calories, Integer protein, Integer fat, Integer sodium,
                    Integer price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        while (title.charAt(title.length() - 1) == ' ') // I don't like the spaces at the end of titles in the csv file
            title = title.substring(0, title.length() - 1);
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Computes the price
     *
     * @return  The price, Integer
     */
    public abstract Integer computePrice();

    @Override
    public String toString() {
        return "MenuItem{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", price=" + price +
                '}';
    }
}
