package com.example.demo.mappers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<ProductDto, Product>{

    @Override
    public Product toModel(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        return product;
    }

    @Override
    public Product toModelPartially(ProductDto dto, Product product) {
        if(dto.name() != null && !dto.name().isEmpty()) product.setName(dto.name());
        if(dto.price() != null) product.setPrice(dto.price());
        return product;
    }
}
