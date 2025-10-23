package com.example.demo.mappers;

public interface Mapper<D, M> {
    M toModel(D dto);
    M toModelPartially(D dto, M model);
}
