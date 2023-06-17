package com.example.blogApi.controller;

import com.example.blogApi.payload.ApiResponse;
import com.example.blogApi.payload.CommentDto;
import com.example.blogApi.repository.CommentRepo;
import com.example.blogApi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId
                                                   ){
        CommentDto comments=this.commentService.CreateComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(comments, HttpStatus.CREATED);
    }

//    @DeleteMapping("/comment/{comId}")
//    public void deleteComment(@PathVariable Integer comId) {
//        this.commentService.deleteComment(comId);
//    }

    @DeleteMapping("/comment/{comId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer comId) {
        this.commentService.deleteComment(comId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleteed",true),HttpStatus.OK);
    }
}
