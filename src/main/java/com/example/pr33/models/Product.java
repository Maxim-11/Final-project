package com.example.pr33.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private double price;

    @NotBlank(message = "Name is required")
    private String productName;

    @ManyToOne(optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    private Address address;


    @ManyToOne(optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;



    @ManyToMany
    @JoinTable (name="product_review",
            joinColumns=@JoinColumn (name="product_id"),
            inverseJoinColumns=@JoinColumn(name="review_id"))
    private List<Review> reviews;



    @ManyToMany
    @JoinTable (name="product_rating",
            joinColumns=@JoinColumn (name="product_id"),
            inverseJoinColumns=@JoinColumn(name="rating_id"))
    private List<Rating> rating;


    public Product() {

    }

    public Product(long id, String productName, double price, String description, Category category) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }


}
