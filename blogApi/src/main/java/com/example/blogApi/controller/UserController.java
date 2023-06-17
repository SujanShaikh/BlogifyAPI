package com.example.blogApi.controller;


import com.example.blogApi.payload.UserDTO;
import com.example.blogApi.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping( "/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createUserDto=this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable("id") Integer id){
        UserDTO updateUser=this.userService.updateUser(userDTO,id);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Integer id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message","user deleted"));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<UserDTO>> getSingleUser(@PathVariable Integer id){
        return ResponseEntity.ok(Collections.singletonList(this.userService.getUserById(id)));
    }

}
