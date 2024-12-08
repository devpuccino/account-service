package com.devpuccino.accountservice.controller;


import com.devpuccino.accountservice.constant.ResponseConstant;
import com.devpuccino.accountservice.domain.request.CategoryRequest;
import com.devpuccino.accountservice.domain.response.Category;
import com.devpuccino.accountservice.domain.response.CommonResponse;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Insert Category",
            tags = {"Category"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommonResponse.class)
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public CommonResponse insertCategory(@RequestBody CategoryRequest request) throws Exception {
        categoryService.insertCategory(request);
        CommonResponse response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        return response;
    }

    @Operation(
            summary = "Get All Category",
            tags = {"Category"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    @GetMapping
    public CommonResponse<List<Category>> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        CommonResponse<List<Category>> response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(categoryList);
        return response;
    }

    @Operation(
            summary = "Get Category by Id",
            tags = {"Category"},
            parameters = {@Parameter(name = "category-id", description = "The ID of Category", in = ParameterIn.PATH)}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    @GetMapping("/{category-id}")
    public CommonResponse<Category> getCategoryById(@PathVariable("category-id") String id) throws DataNotFoundException {
        Category category = categoryService.getById(id);
        CommonResponse<Category> response = new CommonResponse();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        response.setData(category);
        return response;
    }

    @Operation(
            summary = "Delete Category",
            tags = {"Category"},
            parameters = {@Parameter(name = "category-id", description = "The ID of Category", in = ParameterIn.PATH)}
    )
    @DeleteMapping("/{category-id}")
    public CommonResponse deleteCategoryById(@PathVariable("category-id") String id) throws DataNotFoundException {
        categoryService.deleteCategoryById(id);
        CommonResponse response = new CommonResponse<>();
        response.setCode(ResponseConstant.SUCCESS_CODE);
        response.setMessage(ResponseConstant.SUCCESS_MESSAGE);
        return response;
    }

}
