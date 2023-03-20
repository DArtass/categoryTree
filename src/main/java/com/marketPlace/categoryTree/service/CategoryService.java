package com.marketPlace.categoryTree.service;

import com.marketPlace.categoryTree.dto.CategoryDto;
import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface CategoryService {

    Category saveCategory(CategoryDto categoryDto, String language) throws ValidationException;
    void deleteCategory(String name);
    void forceDeleteCategory(String name);
    Page<Category> getList(Pageable pageable);
    Page<Category> getTree(Pageable pageable);
}
