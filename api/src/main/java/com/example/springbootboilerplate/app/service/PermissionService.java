package com.example.springbootboilerplate.app.service;

import com.example.springbootboilerplate.app.dto.common.request.PaginationRequest;
import com.example.springbootboilerplate.app.dto.permission.request.PermissionRequest;
import com.example.springbootboilerplate.app.dto.common.response.PaginationResponse;
import com.example.springbootboilerplate.app.dto.permission.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> findAll();
    PaginationResponse<PermissionResponse> findAllWithPagination(PaginationRequest request);
    PermissionResponse findById(Integer id);
    PermissionResponse create(PermissionRequest.CreatePermissionRequest request);
    PermissionResponse update(Integer id, PermissionRequest.UpdatePermissionRequest request);
    void delete(Integer id);
}
