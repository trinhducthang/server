package com.kachina.identity_service.controller;

import java.util.*;

import com.kachina.identity_service.dto.request.ChangePasswordRequest;
import com.kachina.identity_service.dto.request.UserUpdationRequest;
import com.kachina.identity_service.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import com.kachina.identity_service.service.UserService;
import com.kachina.identity_service.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfor() {
        return userService.getMyInfor();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/update/my-info")
    public ApiResponse<UserResponse> update(@RequestBody UserUpdationRequest request) {
        return userService.updateMyInfo(request);
    }

    @PutMapping("/change-password")
    public ApiResponse<Boolean> changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

}
