package com.marketPlace.categoryTree.service;

import com.marketPlace.categoryTree.dto.CategoryDto;
import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.entity.Dictionary;
import com.marketPlace.categoryTree.exception.ValidationException;
import com.marketPlace.categoryTree.repository.CategoryRepository;
import com.marketPlace.categoryTree.repository.DictionaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DictionaryRepository dictionaryRepository;


    @Override
    public Category saveCategory(CategoryDto categoryDto, String language) throws ValidationException {
        validateCategoryDto(categoryDto);

        Category child = null;
        Category parent = null;
        if (!isNull(categoryDto.getName()) && !categoryDto.getName().isEmpty())
            child = saveCategoryByName(categoryDto.getName(), language);
        if (!isNull(categoryDto.getParentName()) && !categoryDto.getParentName().isEmpty())
            parent = saveCategoryByName(categoryDto.getParentName(), language);

        if (!(child == null) && !(parent == null)) {
            Set<Category> parents = child.getParents();
            if (parents == null)
                parents = new HashSet<>();
            parents.add(parent);
            child.setParents(parents);
            Set<Category> children = parent.getChildren();
            if (children == null)
                children = new HashSet<>();
            children.add(parent);
            parent.setChildren(children);
            categoryRepository.save(child);
        }

        return child;
    }

    private Category saveCategoryByName(String name, String language) {
        Category category = null;
        Dictionary dictionary = dictionaryRepository.findByValue(name);
        if (dictionary == null) {
            category = new Category();
            category.setName(name);
            categoryRepository.save(category);

            Long categoryId = category.getId();

            dictionary = new Dictionary();
            dictionary.setValue(name);
            dictionary.setCategoryId(categoryId);
            dictionary.setLanguage(language);
            dictionaryRepository.save(dictionary);
        }
        else
            category = categoryRepository.findById(dictionary.getCategoryId()).get();
        return category;
    }

    private void validateCategoryDto(CategoryDto categoryDto) throws ValidationException {
        if (isNull(categoryDto)) {
            throw new ValidationException("Object category is null");
        }
    }

    @Override
    public void deleteCategory(String name) {
        Dictionary dictionary = dictionaryRepository.findByValue(name);
        Optional<Category> category = categoryRepository.findById(dictionary.getCategoryId());
        categoryRepository.delete(category.get());
    }

    @Override
    public void forceDeleteCategory(String name) {
        Dictionary dictionary = dictionaryRepository.findByValue(name);
        Optional<Category> category = categoryRepository.findById(dictionary.getCategoryId());
        categoryRepository.deleteAll(category.get().getChildren());
        categoryRepository.delete(category.get());
    }

    @Override
    public Page<Category> getTree(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> getList(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
