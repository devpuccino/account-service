package com.devpuccino.accountservice.controller;


import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.request.CategoryRequest;
import com.devpuccino.accountservice.domain.response.Category;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.service.CategoryService;
import io.micrometer.observation.annotation.Observed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_CODE;
import static com.devpuccino.accountservice.constant.ResponseConstant.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Logger logger = LogManager.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CommonResponse insertCategory(@RequestBody CategoryRequest request) throws Exception {
        categoryService.insertCategory(request);
        return CommonResponse.builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).build();
    }

    @GetMapping
    public CommonResponse<List<Category>> getAllCategory() {
        logger.info("Get All Categories");
        List<Category> categoryList = categoryService.getAllCategory();
        return CommonResponse.<List<Category>>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(categoryList).build();
    }

    @PutMapping("/{id}")
    public CommonResponse<Category> updateCategoryById(@PathVariable("id") String id, @RequestBody CategoryRequest request) throws Exception {
        Category result = categoryService.updateCategoryById(id, request);
        return CommonResponse.<Category>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(result).build();
    }

    @GetMapping("/{id}")
    @Observed(name = "api:get-category-by-id")
    public CommonResponse<Category> getCategoryById(@PathVariable("id") String id) throws DataNotFoundException {
        Category category = categoryService.getById(id);
        return CommonResponse.<Category>builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).data(category).build();
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteCategoryById(@PathVariable("id") String id) throws DataNotFoundException {
        categoryService.deleteCategoryById(id);
        return CommonResponse.builder().code(SUCCESS_CODE).message(SUCCESS_MESSAGE).build();
    }

}
