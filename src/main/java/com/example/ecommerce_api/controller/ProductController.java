package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.dto.Category.CategoryRequest;
import com.example.ecommerce_api.dto.Product.ProductRequest;
import com.example.ecommerce_api.dto.Product.ProductResponse;
import com.example.ecommerce_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {

        ProductResponse newProductResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(newProductResponse, HttpStatus.CREATED);

    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> readAllProducts() {

        return new ResponseEntity<>(productService.readProducts(), HttpStatus.OK);

    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable long id,
            @Valid @RequestBody ProductRequest request
    ) {

        try { productService.updateProduct(id, request); } catch (RuntimeException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } return  new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable long id) {

        try { productService.deleteProduct(id); } catch (RuntimeException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/api/products/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam String categoryName, long id) {

        CategoryRequest categoryRequest = new CategoryRequest(categoryName);
        List<ProductResponse> products = productService.getProductsByCategory(categoryRequest, id);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

}