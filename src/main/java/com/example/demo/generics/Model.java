package com.example.demo.generics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Model<PK> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @JsonIgnore
    private Boolean active = true;

    public PK getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
