package com.marketPlace.categoryTree.prototype;

import com.marketPlace.categoryTree.dto.CategoryDto;
import com.marketPlace.categoryTree.entity.Category;

public class CategoryPrototype {

    public static Category aCategory() {
        Category u = new Category();
        u.setName("test_name");
        return u;
    }

    public static CategoryDto aCategoryDTO() {
        return CategoryDto.builder()
                .name("test_name")
                .parentName("test_parent_name")
                .build();
    }
}
