package com.example.demo.controllers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.generics.Controller;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController extends Controller<Product, Long, ProductRepository, ProductDto, ProductService> {
    public ProductController(ProductService service) {
        super(service);
    }
}
