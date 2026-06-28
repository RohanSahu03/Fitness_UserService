package com.fitness.userservice.services;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo repo;

    public UserResponse register(RegisterRequest registerRequest) {

        if(repo.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email Already Exist");
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setEmail(registerRequest.getEmail());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(registerRequest.getPassword());

        User savedUser = repo.save(user);
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setPassword(savedUser.getPassword());
        response.setEmail(savedUser.getEmail());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;

    }

    public UserResponse getUserProfile(String id){
        User user = repo.findById(id)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setPassword(user.getPassword());
        response.setEmail(user.getEmail());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public Boolean existByUserId(String userId) {
        return repo.existsById(userId);
    }
}
