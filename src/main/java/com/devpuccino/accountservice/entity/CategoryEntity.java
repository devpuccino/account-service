package com.devpuccino.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "category")
@Entity
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="is_active")
    private boolean isActive;
}
