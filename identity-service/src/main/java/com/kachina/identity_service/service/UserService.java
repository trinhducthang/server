package com.kachina.identity_service.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

import net.htmlparser.jericho.CharacterReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kachina.identity_service.dto.request.*;
import com.kachina.identity_service.dto.response.*;
import com.kachina.identity_service.jwt.*;
import com.kachina.identity_service.repository.*;
import com.kachina.identity_service.entity.*;
import com.kachina.identity_service.mapper.*;
import com.kachina.identity_service.exception.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    public ApiResponse<UserResponse> createCandidate(CandidateCreationRequest request) {
        if(userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new AppException("Email \"" + request.getEmail() + "\" đã tồn tại!");
        }

        User candidate = userMapper.toUser(request);
        candidate = userRepository.save(candidate);

        return ApiResponse.<UserResponse>builder()
                .status(201)
                .message("Đăng ký tài khoản thành công!")
                .result(userMapper.toUserResponse(candidate))
                .build();
    }

    public ApiResponse<UserResponse> createEmployer(EmployerCreationRequest request) {
        if(userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new AppException("Email \"" + request.getEmail() + "\" đã tồn tại!");
        }

        User employer = userMapper.toUser(request);
        employer = userRepository.save(employer);

        return ApiResponse.<UserResponse>builder()
                .status(201)
                .message("Đăng ký tài khoản thành công!")
                .result(userMapper.toUserResponse(employer))
                .build();
    }

    public ApiResponse<AuthResponse> getUser(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        if(!user.isEnabled()) {
            throw new AppException("Tài khoản người dùng không được phép truy cập!");
        }

        AuthResponse body = AuthResponse.builder()
                .accessToken(jwtUtils.generateToken(user))
                .user(userMapper.toUserResponse(user))
                .build();

        return ApiResponse.<AuthResponse>builder()
                .status(200)
                .message("Đăng nhập thành công!")
                .result(body)
                .build();
    }

    public ApiResponse<UserResponse> getMyInfor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        return ApiResponse.<UserResponse>builder()
                .status(200)
                .result(userMapper.toUserResponse(user))
                .build();
    }

    public ApiResponse<UserResponse> updateMyInfo(UserUpdationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        
        user.setCompany_name(CharacterReference.encode(request.getCompany_name()));
        user.setFull_name(CharacterReference.encode(request.getFull_name()));
        user.setUpdated_at(new Date());
        user.setAddress(CharacterReference.encode(request.getAddress()));
        user.setGender(request.getGender());
        user.setPhone_number(request.getPhone_number());
        if(request.getAvatar_url() != null && !request.getAvatar_url().equals("")) {
            String public_id = "avatar_user_" + user.getId();
            try {
                String avatar_url = cloudinaryService.uploadBase64Image(request.getAvatar_url(), public_id);
                user.setAvatar_url(avatar_url);
            } catch (IOException e) {
                throw new RuntimeException("Tải ảnh không thành công!");
            }
        }

        user = userRepository.save(user);

        return ApiResponse.<UserResponse>builder()
                .status(201)
                .message("Cập nhật thông tin thành công!")
                .result(userMapper.toUserResponse(user))
                .build();
    }

    public ApiResponse<Boolean> changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        if(!passwordEncoder.matches(request.getCurrent_password(), user.getPassword())) {
            throw new AppException("Mật khẩu cũ không chính xác!");
        }

        if(!request.getNew_password().equals(request.getConfirm_password())) {
            throw new AppException("Mật khẩu xác nhận và mật khẩu mới không khớp!");
        }

        user.setPassword(passwordEncoder.encode(request.getConfirm_password()));
        userRepository.save(user);

        return ApiResponse.<Boolean>builder()
                .status(201)
                .result(true)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new NotFoundException("User not found with id: " + userId);
        return userMapper.toUserResponse(user.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = users.stream()
            .map(user -> userMapper.toUserResponse(user))
            .collect(Collectors.toList());
        return response;
    }

}
