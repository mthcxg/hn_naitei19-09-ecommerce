package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.dao.CategoryRepository;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return repo.save(categorySave);
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.setIs_activated(category.isIs_activated());
            categoryUpdate.setIs_deleted(category.isIs_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = repo.getReferenceById(id);
        category.setIs_deleted(true);
        category.setIs_activated(false);
        repo.save(category);
    }

    @Override
    public void enabledById(Long id) {
        Category category = repo.getReferenceById(id);
        category.setIs_deleted(true);
        category.setIs_activated(false);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repo.findAllByActivated();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return repo.getCategoryAndProduct();
    }
}