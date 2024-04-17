package com.uku.productservice.services;

import com.uku.productservice.exceptions.ProductNotFoundException;
import com.uku.productservice.models.Product;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;

}
