package com.example.demo.services;

import com.example.demo.dtos.ProductDto;
import com.example.demo.mappers.Mapper;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends com.example.demo.services.Service<Product, Long, ProductRepository, ProductDto> {

    public ProductService(ProductRepository repository, Mapper<ProductDto, Product> mapper) {
        super(repository, mapper);
    }
}
