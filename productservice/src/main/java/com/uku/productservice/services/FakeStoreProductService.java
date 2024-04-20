package com.uku.productservice.services;

import com.uku.productservice.dtos.FakeStoreProductDto;
import com.uku.productservice.exceptions.ProductNotFoundException;
import com.uku.productservice.models.Category;
import com.uku.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(url, FakeStoreProductDto[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            FakeStoreProductDto[] productDtos = responseEntity.getBody();
            if (productDtos != null && productDtos.length > 0) {
                return Arrays.stream(productDtos)
                        .map(this::convertFakeStoreProductToProduct)
                        .collect(Collectors.toList());
            } else {
                throw new ProductNotFoundException("No products found");
            }
        } else {
            throw new ProductNotFoundException("Failed to retrieve products");
        }



    }

    @Override
    public ResponseEntity<Product> addNewProduct(Product product){
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(url, product, Product.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) throws ProductNotFoundException{
        try {
            restTemplate.delete( "https://fakestoreapi.com/products/" + id);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long id, Product product) {
        restTemplate.patchForObject("https://fakestoreapi.com/products/" + id, product, FakeStoreProductDto.class);
        return  ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id, Product product){
        restTemplate.put("https://fakestoreapi.com/products/" + id, product, FakeStoreProductDto.class);
        return ResponseEntity.ok().build();
    }

}


