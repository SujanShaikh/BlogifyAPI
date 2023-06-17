package com.example.blogApi.service.impl;

import com.example.blogApi.exception.ResourceNotFoundException;
import com.example.blogApi.model.Category;
import com.example.blogApi.model.Post;
import com.example.blogApi.model.User;
import com.example.blogApi.payload.PostDto;
import com.example.blogApi.payload.PostResponse;
import com.example.blogApi.repository.CategoryRepo;
import com.example.blogApi.repository.PostRepo;
import com.example.blogApi.repository.UserRepo;
import com.example.blogApi.service.FileService;
import com.example.blogApi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User id", userId));

        Category category = this.categoryRepo.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category Id", catId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedpost = this.postRepo.save(post);
        return this.modelMapper.map(updatedpost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        this.postRepo.deleteById(postId);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        return  this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNo, Integer pageSize,String sortBy,String sortDir) {

        Sort sort=null;
            if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
            }
            else{
                sort=Sort.by(sortBy).descending();
            }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = this.postRepo.findAll(pageable);
        List<PostDto> postDtos = posts.getContent().stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement((int) posts.getTotalElements());
        postResponse.setTotalPages((posts.getTotalPages()));
        postResponse.setLastPage(posts.isLast());
        return postResponse;
    }


    @Override
    public List<PostDto> getPostByCategory(Integer catId) {
        Category category = this.categoryRepo.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category id", catId));
        List<Post> postList = this.postRepo.findByCategory(category);
        List<PostDto> postDtoList = postList.stream().map(post ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));
        List<Post> users = this.postRepo.findByUser(user);
        List<PostDto> postDtos = users.stream().map(user1 ->
                this.modelMapper.map(user1, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }



}
