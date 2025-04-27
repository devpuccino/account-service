package com.devpuccino.accountservice.domain.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String categoryName;
    private Boolean isActive;
}
