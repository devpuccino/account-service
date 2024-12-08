package com.devpuccino.accountservice.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Category {
    @Schema(title="Category Id",example = "1")
    private String id;
    @Schema(title="Category name",example = "Salary")
    private String categoryName;
    @Schema(title="Active flag",examples = {"true","false"})
    private boolean isActive;
    @Schema(title="User Id of the user who created this category",example = "user-id-1")
    private String ownerId;
}
