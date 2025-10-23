package com.example.demo.mappers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<ProductDto, Product>{

    @Autowired
    private ProductRepository repository;

    @Override
    public Product toModel(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        return product;
    }

    @Override
    public void updateModel(ProductDto dto, Product model) {
        model.setName(dto.name());
        model.setPrice(dto.price());
    }

    @Override
    public void updateModelPartially(ProductDto dto, Product model) {
        if(dto.name() != null && !dto.name().isEmpty()) model.setName(dto.name());
        if(dto.price() != null) model.setPrice(dto.price());
    }
}
