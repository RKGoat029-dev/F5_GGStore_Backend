package com.example.ecommerce_api.Category.dto;

import com.example.ecommerce_api.Category.model.Category;

public class CategoryMapper {

    public static Category DTOToEntity(CategoryRequest categoryRequest) { return new Category(categoryRequest.username()); }

    public static CategoryResponse EntityToDTO(Category category) { return new CategoryResponse(category.getName()); }

}
