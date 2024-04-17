package com.uku.productservice.services;

import com.uku.productservice.dtos.FakeStoreProductDto;
import com.uku.productservice.exceptions.ProductNotFoundException;
import com.uku.productservice.models.Category;
import com.uku.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;


    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private  Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setId(fakeStoreProductDto.getId());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImageUrl());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());


        return product;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        System.out.println("Service: Retrieving product with ID " + id);
        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );
        if (productDto == null) {
            throw new ProductNotFoundException("Service: Product with ID " + id + " does not exist");
        }
        Product product = convertFakeStoreProductToProduct(productDto);
        System.out.println("Service: Product retrieved successfully: " + product);
        return product;
    }

//    @Override
//    public List<Product> getAllProduct(){
//        FakeStoreProductDto productDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products",
//                FakeStoreProductDto.class
//        );
//
//    }
}
