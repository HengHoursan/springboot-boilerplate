package com.example.springbootboilerplate.app.service;

import com.example.springbootboilerplate.app.dto.common.request.IdRequest;
import com.example.springbootboilerplate.app.dto.common.request.PaginationRequest;
import com.example.springbootboilerplate.app.dto.user.request.UserRequest;
import com.example.springbootboilerplate.app.dto.common.response.PaginationResponse;
import com.example.springbootboilerplate.app.dto.user.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    PaginationResponse<UserResponse> findAllWithPagination(PaginationRequest request);
    UserResponse findById(Integer id);
    UserResponse create(UserRequest.CreateUserRequest request);
    UserResponse update(Integer id, UserRequest.UpdateUserRequest request);
    void softDelete(IdRequest request);
    void forceDelete(IdRequest request);
}
