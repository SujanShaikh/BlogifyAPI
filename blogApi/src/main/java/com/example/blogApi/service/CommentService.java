package com.example.blogApi.service;


import com.example.blogApi.payload.CommentDto;

public interface CommentService {

    CommentDto CreateComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer comId);

}
