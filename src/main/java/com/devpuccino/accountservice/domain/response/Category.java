package com.devpuccino.accountservice.domain.response;

import lombok.Data;

@Data
public class Category {
    private String id;
    private String categoryName;
    private boolean isActive;
}
