package com.devpuccino.accountservice.controller;


import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.request.CategoryRequest;
import com.devpuccino.accountservice.domain.response.Category;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CommonResponse insertCategory(@RequestBody CategoryRequest request) throws Exception {
        categoryService.insertCategory(request);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        return response;
    }

    @GetMapping
    public CommonResponse<List<Category>> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        CommonResponse<List<Category>> response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(categoryList);
        return response;
    }

    @GetMapping("/{id}")
    public CommonResponse<Category> getCategoryById(@PathVariable("id") String id) throws DataNotFoundException {
        Category category = categoryService.getById(id);
        CommonResponse<Category> response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(category);
        return response;
    }

}
