package com.example.demo.models;

import com.example.demo.generics.Model;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product extends Model<Long> {

    @Column(name="name", nullable = false)
    private String name;
    private Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
