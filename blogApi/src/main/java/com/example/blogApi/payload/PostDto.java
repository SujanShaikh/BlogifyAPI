package com.example.blogApi.payload;

import com.example.blogApi.model.Category;
import com.example.blogApi.model.Comment;
import com.example.blogApi.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private int postId;
    @NotBlank(message = "title can't be empty")
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDTO user;
    private Set<Comment> commentSet = new HashSet<>();

}
