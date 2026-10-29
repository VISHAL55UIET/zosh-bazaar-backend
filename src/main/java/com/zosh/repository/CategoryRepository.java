package com.zosh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCategoryId(String categoryId);

    default Category findByCategoryId(String categoryId) {
        List<Category> list = findAllByCategoryId(categoryId);
        return list.isEmpty() ? null : list.get(0);
    }

    List<Category> findByLevel(Integer level);
}