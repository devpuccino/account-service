package com.devpuccino.accountservice.service;

import com.devpuccino.accountservice.domain.request.CategoryRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static Logger logger = LogManager.getLogger(CategoryService.class);

    public void insertCategory(CategoryRequest request){
       logger.info("Receive Request = {}  {}",request);
    }
}
