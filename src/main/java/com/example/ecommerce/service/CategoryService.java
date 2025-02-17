package com.example.ecommerce.service;


import java.util.List;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Product;



public interface CategoryService {
    /*Admin*/
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enabledById(Long id);
    List<Category> findAllByActivated();

    /*Customer*/
    List<CategoryDto> getCategoryAndProduct();


}