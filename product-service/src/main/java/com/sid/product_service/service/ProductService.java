package com.sid.product_service.service;

import com.sid.product_service.dto.ProductRequest;
import com.sid.product_service.dto.ProductResponse;
import com.sid.product_service.model.Product;
import com.sid.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder().
                name(productRequest.getName()).
                description(productRequest.getDescription()).
                price(productRequest.getPrice()).
                build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::mapProductResponse).toList();
    }

    private ProductResponse mapProductResponse(Product product) {
        return ProductResponse.builder().name(product.getName()).id(product.getId()).description(product.getDescription()).price(product.getPrice()).build();
    }

}
