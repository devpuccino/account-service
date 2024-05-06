package com.devpuccino.accountservice.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping
    public String getAllCategory()
    {
        return "hello";
    }
    @GetMapping(path="/{id}")
    public String getCategoryById(@PathVariable("id") String id,@RequestParam("name") String name){
        return "Hello "+id+" name="+name;
    }


}
