package com.devpuccino.accountservice.repository;

import com.devpuccino.accountservice.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    List<CategoryEntity> findByCategoryName(String categoryName) throws Exception;
}
