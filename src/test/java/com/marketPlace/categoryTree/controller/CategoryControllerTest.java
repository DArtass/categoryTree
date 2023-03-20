package com.marketPlace.categoryTree.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.marketPlace.categoryTree.prototype.CategoryPrototype.aCategory;
import static com.marketPlace.categoryTree.prototype.CategoryPrototype.aCategoryDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveCategory() throws Exception {
        when(categoryService.saveCategory(any(), "en")).thenReturn(aCategory());
        mockMvc.perform(post("/category/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aCategoryDTO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(aCategoryDTO())));
    }

    @Test
    void findAllCategory() throws Exception {
        when(categoryService.getList(PageRequest.of(1, 10, Sort.by("id")))).thenReturn((Page<Category>) Collections.singletonList(aCategory()));
        mockMvc.perform(get("/category/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(aCategoryDTO()))))
                .andExpect(status().isOk());
    }
}
