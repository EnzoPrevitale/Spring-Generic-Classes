package com.example.demo.generics;

public interface Mapper<D, M> {
    M toModel(D dto);
    void updateModel(D dto, M model);
    void updateModelPartially(D dto, M model);
}
