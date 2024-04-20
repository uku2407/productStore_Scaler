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

@RestController
@RequestMapping("/products")
public class ProductController {

   private ProductService productService;

   @Autowired
   public  ProductController(ProductService productService){
       this.productService = productService;
   }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException{
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        try {
            Product product = productService.getSingleProduct(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product)
    throws ProductNotFoundException{
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }


}
