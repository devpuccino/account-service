package com.devpuccino.accountservice.controller;


import com.devpuccino.accountservice.domain.request.CategoryRequest;
import com.devpuccino.accountservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public void insertCategory(@RequestBody CategoryRequest request) {
        categoryService.insertCategory(request);
    }

    @GetMapping
    public List<CategoryRequest> getAllCategory() {
        List<CategoryRequest> responseList = new ArrayList<>();

        CategoryRequest response = new CategoryRequest();
        response.setIsActive(true);
        response.setCategoryName("Food");

        CategoryRequest response2 = new CategoryRequest();
        response.setIsActive(true);
        response.setCategoryName("Internet");
        responseList.add(response);
        responseList.add(response2);
        return responseList;
    }

    @GetMapping(path = "/{id}")
    public CategoryRequest getCategoryById(@PathVariable("id") String id) {
        CategoryRequest response = new CategoryRequest();
        response.setIsActive(true);
        response.setCategoryName("Food");
        return response;
    }


}
