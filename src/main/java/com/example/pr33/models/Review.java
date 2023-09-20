package com.example.pr33.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Number is required")
    private String number;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Text is required")
    private String text;

    @ManyToMany
    @JoinTable(name="prod_review",
            joinColumns=@JoinColumn(name="review_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products;


    public Review() {

    }

    public Review(int id, String number, String username, String text) {
        this.id = id;
        this.number = number;
        this.username = username;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
