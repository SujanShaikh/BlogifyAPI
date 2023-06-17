package com.example.blogApi.service.impl;

import com.example.blogApi.exception.ResourceNotFoundException;
import com.example.blogApi.model.User;
import com.example.blogApi.payload.UserDTO;
import com.example.blogApi.repository.UserRepo;
import com.example.blogApi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    private User dtoToUser(UserDTO userDTO){
        User user=this.modelMapper.map(userDTO,User.class);
        return user;
    }
    private UserDTO userToDTO(User user){
        UserDTO userDTO= this.modelMapper.map(user,UserDTO.class);
        return userDTO;
    }
    @Override
    public UserDTO createUser(UserDTO userDTO) {
          User user=this.dtoToUser(userDTO);
          User SavedUser=this.userRepo.save(user);
        return this.userToDTO(SavedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user= this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser=this.userRepo.save(user);
        UserDTO userDTO1=this.userToDTO(updatedUser);
        return userDTO1;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user= this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
        return this.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users=this.userRepo.findAll();
        List<UserDTO>  userDTOs=users.stream().map(user -> this.userToDTO(user)).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public void deleteUser(Integer id) {
        User user=this.userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",id));
        this.userRepo.delete(user);

    }
}
