package com.marketPlace.categoryTree.controller;

import com.marketPlace.categoryTree.dto.CategoryDto;
import com.marketPlace.categoryTree.entity.Category;
import com.marketPlace.categoryTree.exception.ValidationException;
import com.marketPlace.categoryTree.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@Log
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto,
                                    @RequestHeader("Accept-Language") String language) throws ValidationException {
        log.info("Handling save category: " + categoryDto);
        categoryService.saveCategory(categoryDto, language);
        return categoryDto;
    }

    @GetMapping("/getList")
    public String getListCategory(Model model, @RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        log.info("Handling get list category request");
        Page<Category> categoryPage = categoryService.getList(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("currentPage", pageNo);
        return "listCategories";
    }

    @GetMapping("/getTree")
    public String getTreeCategory(Model model, @RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        log.info("Handling get tree category request");
        Page<Category> categoryPage = categoryService.getTree(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("currentPage", pageNo);
        return "treeCategories";
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        log.info("Handling delete category request: " + name);
        categoryService.deleteCategory(name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/force-delete/{name}")
    public ResponseEntity<Void> forceDeleteCategory(@PathVariable String name) {
        log.info("Handling force delete category request: " + name);
        categoryService.forceDeleteCategory(name);
        return ResponseEntity.ok().build();
    }
}
