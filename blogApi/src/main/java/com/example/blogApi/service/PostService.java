package com.example.blogApi.service;

import com.example.blogApi.model.Post;
import com.example.blogApi.payload.PostDto;
import com.example.blogApi.payload.PostResponse;
import com.example.blogApi.payload.UserDTO;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer catId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPostById(Integer postId); // Updated return type

   PostResponse getAllPost(Integer pageNo, Integer pageSize,String sortBy,String sortDir);

    List<PostDto> getPostByCategory(Integer catId);

    List<PostDto> getPostByUser(Integer userId);

    //keyword searching
    List<PostDto> searchPost(String keyword);

}

