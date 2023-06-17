package com.example.blogApi.service;
import com.example.blogApi.payload.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user,Integer id);
    UserDTO getUserById(Integer id);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer id);

}
