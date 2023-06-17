package com.example.blogApi.payload;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotEmpty(message = "it can't be empty")
    private String CategoryName;
    @NotEmpty(message = "it can't be empty")
    private String CategoryDescription;
}
