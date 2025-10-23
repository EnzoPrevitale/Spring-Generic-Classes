package com.example.demo.mappers;

public interface Mapper<D, M> {
    M toModel(D dto);
    void updateModel(D dto, M model);
    void updateModelPartially(D dto, M model);
}
