package com.uku.productservice.services;

import com.uku.productservice.exceptions.ProductNotFoundException;
import com.uku.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;

    ResponseEntity<Product> addNewProduct(Product product);

    ResponseEntity<Product> deleteProduct(Long id) throws ProductNotFoundException;

    ResponseEntity<Product> updateProduct(Long id, Product product) ;

    ResponseEntity<Product> replaceProduct(Long id, Product product);
}
