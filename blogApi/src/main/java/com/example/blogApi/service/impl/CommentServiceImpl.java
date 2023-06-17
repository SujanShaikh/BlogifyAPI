package com.example.blogApi.service.impl;

import com.example.blogApi.exception.ResourceNotFoundException;
import com.example.blogApi.model.Comment;
import com.example.blogApi.model.Post;
import com.example.blogApi.model.User;
import com.example.blogApi.payload.CommentDto;
import com.example.blogApi.repository.CommentRepo;
import com.example.blogApi.repository.PostRepo;
import com.example.blogApi.repository.UserRepo;
import com.example.blogApi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto CreateComment(CommentDto commentDto, Integer postId) {
//        User user = this.userRepo.findById(userId).orElseThrow(() ->
//                new ResourceNotFoundException("User", "user id", userId));

        Post post = this.postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
//        comment.setUser(user);
        comment.setPost(post);

        Comment newComment = this.commentRepo.save(comment);
        return this.modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer comId) {
        this.commentRepo.findById(comId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "com id", comId));
        this.commentRepo.deleteById(comId);
    }
}
