package com.example.blogApi.controller;

import com.example.blogApi.config.AppConstants;
import com.example.blogApi.model.Post;
import com.example.blogApi.payload.*;
import com.example.blogApi.repository.PostRepo;
import com.example.blogApi.service.FileService;
import com.example.blogApi.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @Autowired
    private ModelMapper modelMapper;
@Autowired
    private PostRepo postRepo;
    @PostMapping("/user/{userId}/category/{catId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer catId) {
        PostDto post = this.postService.createPost(postDto, userId, catId);
        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{catId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer catId) {
        List<PostDto> posts = this.postService.getPostByCategory(catId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo" ,defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        PostResponse postResponse=this.postService.getAllPost(pageNum,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return  ResponseEntity.ok( this.postService.getPostById(postId));
    }
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Integer postId){

        this.postService.deletePost(postId);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedpost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedpost,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchKeyword(@PathVariable("keyword") String keyword) {
        List<PostDto> result = this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }
    //Image upload Path
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        if (postDto == null) {
            // Handle the case when no post is found with the given ID
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String filename = this.fileService.uploadImage(path, image);
        postDto.setImageName(filename);
        // Update the PostDto in the service layer instead of directly updating the entity
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

//    serve files
    @GetMapping(value = "post/image/{image}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("image") String image,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource=this.fileService.getResource(path,image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }




}
