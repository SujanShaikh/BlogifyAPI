package com.example.blogApi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor@Getter@Setter@AllArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPages;
    private boolean lastPage;
}
