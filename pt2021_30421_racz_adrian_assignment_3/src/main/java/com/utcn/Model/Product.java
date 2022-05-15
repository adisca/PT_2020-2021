package com.utcn.Model;

/**
 * Model class for product
 */
public class Product {
    private Integer id;
    private String name;
    private String provider;
    private String category;

    public Product() {

    }

    public Product(Integer id, String name, String provider, String category) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
