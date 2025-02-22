package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.Category.CategoryRequest;
import com.example.ecommerce_api.dto.Product.ProductMapper;
import com.example.ecommerce_api.dto.Product.ProductRequest;
import com.example.ecommerce_api.dto.Product.ProductResponse;
import com.example.ecommerce_api.exceptions.EmptyException;
import com.example.ecommerce_api.exceptions.ObjectNotFoundException;
import com.example.ecommerce_api.model.Category;
import com.example.ecommerce_api.model.Product;
import com.example.ecommerce_api.repository.iCategoryRepository;
import com.example.ecommerce_api.repository.iProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final iProductRepository iProductRepository;
    private final iCategoryRepository iCategoryRepository;

    public ProductService (
            iProductRepository iProductRepository,
            iCategoryRepository iCategoryRepository
    ) {
        this.iProductRepository = iProductRepository;
        this.iCategoryRepository = iCategoryRepository;
    }

    public ProductResponse createProduct (ProductRequest newProductRequest) {

        Product newProduct = ProductMapper.DTOToEntity(newProductRequest, iCategoryRepository);
        Product savedProduct = iProductRepository.save(newProduct);

        return ProductMapper.EntityToDTO(savedProduct);

    }

    public List<ProductResponse> readProducts() {

        List<Product> products = iProductRepository.findAll();

        if (products.isEmpty()) throw new EmptyException();

        return products.stream().map(ProductMapper::EntityToDTO).toList();

    }

    public Product updateProduct (long id, ProductRequest productRequest) {

        Optional<Product> foundProduct = iProductRepository.findById(id);
        Optional<Category> optionalCategory = iCategoryRepository.findById(productRequest.categoryId());

        if (foundProduct.isPresent()) {

            Product existingProduct = foundProduct.get();

            existingProduct.setPrice(productRequest.price());
            existingProduct.setName(productRequest.name());
            existingProduct.setImageURL(productRequest.imageURL());
            optionalCategory.ifPresent(existingProduct::setCategory);

            return iProductRepository.save(existingProduct);

        } throw new ObjectNotFoundException("product", id);

    }

    public void deleteProduct (long id) {

        Optional<Product> product = iProductRepository.findById(id);

        if (product.isPresent()) { iProductRepository.deleteById(id); }

    }

    public Optional<ProductResponse> findProductById (long id) {

        Optional<Product> foundProduct = iProductRepository.findById(id);

        if (foundProduct.isPresent()) {

            ProductResponse productResponseById = ProductMapper.EntityToDTO(foundProduct.get());
            return Optional.of(productResponseById);

        } throw new ObjectNotFoundException("product", id);

    }

    public List<ProductResponse> getProductsByCategory (
            CategoryRequest categoryRequest, long id
    ) {

        Optional<Category> foundCategory = iCategoryRepository.findById(categoryRequest.id());

        if (foundCategory.isPresent()) {

            List<Product> productsByCategory = iProductRepository.findByCategory(foundCategory);

            return productsByCategory.stream().map(ProductMapper::EntityToDTO).toList();

        } throw new ObjectNotFoundException("category", id);

    }

}
