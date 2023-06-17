package com.example.blogApi.service.impl;

import com.example.blogApi.exception.ResourceNotFoundException;
import com.example.blogApi.model.Category;
import com.example.blogApi.model.User;
import com.example.blogApi.payload.CategoryDto;
import com.example.blogApi.payload.UserDTO;
import com.example.blogApi.repository.CategoryRepo;
import com.example.blogApi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    public Category dtoToCat(CategoryDto categoryDto){
        Category cat=this.modelMapper.map(categoryDto,Category.class);
        return cat;
    }

    public CategoryDto catToDto(Category category){
        CategoryDto categoryDto=this.modelMapper.map(category,CategoryDto.class);
        return  categoryDto;
    }

    @Override
    public CategoryDto createCat(CategoryDto catDto) {
        Category category=this.dtoToCat(catDto);
        Category SaveCat=this.categoryRepo.save(category);
        return this.catToDto(SaveCat);
    }


    @Override
    public CategoryDto updateCat(CategoryDto catDto, Integer catId) {
         Category category= this.categoryRepo.findById(catId).orElseThrow(
                 ()-> new ResourceNotFoundException("Category","catId",catId));

         category.setCategoryName(catDto.getCategoryName());
         category.setCategoryDescription(catDto.getCategoryDescription());

         Category updatedCat=this.categoryRepo.save(category);
         CategoryDto categoryDto=this.catToDto(updatedCat);
         return categoryDto;
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category category= this.categoryRepo.findById(catId).orElseThrow(
                ()-> new ResourceNotFoundException("Category","catId",catId));
        this.categoryRepo.delete(category);
    }
    @Override
    public CategoryDto getCatById(Integer catId) {

        Category category= this.categoryRepo.findById(catId).orElseThrow(
                ()-> new ResourceNotFoundException("Category","catId",catId));
       return this.catToDto(category);
    }
    @Override
    public List<CategoryDto> allCat() {
        List<Category> category=this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos=category.stream().map(category1 -> this.catToDto(category1)).collect(Collectors.toList());
        return categoryDtos;
    }

}

