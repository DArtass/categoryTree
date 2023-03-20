package com.marketPlace.categoryTree.repository;

import com.marketPlace.categoryTree.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.marketPlace.categoryTree.prototype.CategoryPrototype.aCategory;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByLogin() {
        categoryRepository.save(aCategory());
        Category foundCategory = categoryRepository.findById(aCategory().getId()).get();
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo(aCategory().getName());
    }
}
