package com.example.springbootboilerplate.app.service;

import com.example.springbootboilerplate.app.dto.common.request.PaginationRequest;
import com.example.springbootboilerplate.app.dto.role.request.RoleRequest;
import com.example.springbootboilerplate.app.dto.common.response.PaginationResponse;
import com.example.springbootboilerplate.app.dto.role.response.RoleResponse;
import java.util.List;

public interface RoleService {
    List<RoleResponse> findAll();
    PaginationResponse<RoleResponse> findAllWithPagination(PaginationRequest request);
    RoleResponse findById(Integer id);
    RoleResponse create(RoleRequest.CreateRoleRequest request);
    RoleResponse update(Integer id, RoleRequest.UpdateRoleRequest request);
    void delete(Integer id);
    RoleResponse assignPermissions(Integer roleId, List<Integer> permissionIds);
}
