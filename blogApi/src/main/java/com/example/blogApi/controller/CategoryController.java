package com.example.blogApi.controller;

import com.example.blogApi.payload.CategoryDto;
import com.example.blogApi.payload.UserDTO;
import com.example.blogApi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
       CategoryDto createCategoryDto=this.categoryService.createCat(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catId")Integer catId){
        CategoryDto updateCategory=this.categoryService.updateCat(categoryDto,catId);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<List<CategoryDto>> getSingleCategory( @PathVariable Integer catId){
        return ResponseEntity.ok(Collections.singletonList(this.categoryService.getCatById(catId)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory( ){
        return ResponseEntity.ok(this.categoryService.allCat());
    }



}
