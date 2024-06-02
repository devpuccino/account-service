package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.domain.request.CategoryRequest;
import com.devpuccino.accountservice.entity.CategoryEntity;
import com.devpuccino.accountservice.exception.DuplicateDataException;
import com.devpuccino.accountservice.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private static Logger logger = LogManager.getLogger(CategoryService.class);
    @Autowired
    private CategoryRepository categoryRepository;

    public void insertCategory(CategoryRequest request) throws Exception {
        logger.info("Receive Request = {}  {}", request);
        List<CategoryEntity> categoryEntityList = this.categoryRepository.findByCategoryName(request.getCategoryName());
        if(categoryEntityList == null || categoryEntityList.size() == 0){
            CategoryEntity entity = new CategoryEntity();
            entity.setCategoryName(request.getCategoryName());
            entity.setActive(request.getIsActive());
            this.categoryRepository.save(entity);
            logger.info("Save Request = {}  {}", request);
        }else{
            throw new DuplicateDataException("Duplication data categoryName "+request.getCategoryName());
        }
    }
}
