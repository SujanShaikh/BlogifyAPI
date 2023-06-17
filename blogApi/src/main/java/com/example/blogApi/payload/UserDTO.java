package com.example.blogApi.payload;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotEmpty
    private String name;
    @Email@NotEmpty(message = "email can't be empty")
    private String email;
    @NotEmpty
    @Size(min=4,message = "password should be at least 4 letters ")
    @Pattern(regexp = "[A-Za-z0-9]+")
    private String password;
    private String about;
}
