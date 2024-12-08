package com.devpuccino.accountservice.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryRequest {
    @Schema(title="Category Name", example = "Salary")
    private String categoryName;
    @Schema(title="Active Flag", examples = {"true","false"})
    private Boolean isActive;
}
