package com.example.ecommerce.dao;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value ="select c from Category c where c.is_activated = true and c.is_deleted = false",  nativeQuery = true)
    List<Category> findAllByActivated();


    /*Customer*/
    @Query(value ="select new com.example.ecommerce.dto.CategoryDto(c.id, c.name, count(p.category.id)) from Category c inner join Product p on p.category.id = c.id " +
            " where c.is_activated = true and c.is_deleted = false group by c.id",  nativeQuery = true)
    List<CategoryDto> getCategoryAndProduct();
}
