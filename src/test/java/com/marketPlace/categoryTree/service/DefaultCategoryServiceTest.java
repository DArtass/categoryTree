package com.marketPlace.categoryTree.service;

import com.marketPlace.categoryTree.dto.CategoryDto;
import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.exception.ValidationException;
import com.marketPlace.categoryTree.repository.CategoryRepository;
import com.marketPlace.categoryTree.repository.DictionaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.marketPlace.categoryTree.prototype.CategoryPrototype.aCategory;
import static com.marketPlace.categoryTree.prototype.CategoryPrototype.aCategoryDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCategoryServiceTest {

    private CategoryRepository categoryRepository;
    private DictionaryRepository dictionaryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        dictionaryRepository = mock(DictionaryRepository.class);
        categoryService = new DefaultCategoryService(categoryRepository, dictionaryRepository);
    }

    @Test
    void saveCategory() throws ValidationException {
        when(categoryRepository.save(any())).thenReturn(aCategory());
        Category createdCategory = categoryService.saveCategory(aCategoryDTO(), "en");
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo(aCategoryDTO().getName());
    }

    @Test
    void saveCategoryThrowsValidationExceptionWhenCategoryDtoNull() {
        CategoryDto categoryDto = null;
        assertThrows(ValidationException.class,
                () -> categoryService.saveCategory(categoryDto, "en"),
                "Login is empty");
    }
}
