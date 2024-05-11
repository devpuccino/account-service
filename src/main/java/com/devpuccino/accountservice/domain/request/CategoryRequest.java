package com.devpuccino.accountservice.domain.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class CategoryRequest {
    private String categoryName;
    private Boolean isActive;
}
