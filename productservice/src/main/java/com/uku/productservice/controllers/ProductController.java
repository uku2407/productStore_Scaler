package com.uku.productservice.controllers;

import com.uku.productservice.exceptions.ProductNotFoundException;
import com.uku.productservice.models.Product;
import com.uku.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController("/products")
public class ProductController {

   private ProductService productService;

   @Autowired
   public  ProductController(ProductService productService){
       this.productService = productService;
   }
    @GetMapping
    public List<Product> getAllProducts(){
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        try {
            System.out.println("Controller: Retrieving product with ID " + id);
            Product product = productService.getSingleProduct(id);
            System.out.println("Controller: Product retrieved successfully: " + product);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            System.out.println("Controller: Product not found for ID " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        Product p = new Product();
        return  new Product();
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long id){
        return new Product();
    }


}
