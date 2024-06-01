package com.devpuccino.accountservice.repository;

import com.devpuccino.accountservice.entity.CategoryEntity;
import com.devpuccino.accountservice.exception.DuplicateDataException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    List<CategoryEntity> findByCategoryName(String categoryName) throws Exception;
}
