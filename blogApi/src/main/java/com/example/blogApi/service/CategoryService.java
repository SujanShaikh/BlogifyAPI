package com.example.blogApi.service;

import com.example.blogApi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCat(CategoryDto catDto);
    CategoryDto updateCat(CategoryDto catDto,Integer catId);
    void deleteCategory(Integer catId);
    CategoryDto getCatById(Integer  catId);
    List<CategoryDto> allCat();


}
