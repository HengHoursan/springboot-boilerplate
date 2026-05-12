package com.example.springbootboilerplate.app.service.impl;

import com.example.springbootboilerplate.app.dto.common.request.PaginationRequest;
import com.example.springbootboilerplate.app.dto.role.request.RoleRequest;
import com.example.springbootboilerplate.app.dto.common.response.PaginationMeta;
import com.example.springbootboilerplate.app.dto.common.response.PaginationResponse;
import com.example.springbootboilerplate.app.dto.role.response.RoleResponse;
import com.example.springbootboilerplate.app.exception.common.DuplicateResourceException;
import com.example.springbootboilerplate.app.exception.common.ResourceInUseException;
import com.example.springbootboilerplate.app.exception.common.ResourceNotFoundException;
import com.example.springbootboilerplate.app.models.Permission;
import com.example.springbootboilerplate.app.models.Role;
import com.example.springbootboilerplate.app.models.RolePermission;
import com.example.springbootboilerplate.app.repository.PermissionRepository;
import com.example.springbootboilerplate.app.repository.RoleRepository;
import com.example.springbootboilerplate.app.repository.UserRepository;
import com.example.springbootboilerplate.app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    public List<RoleResponse> findAll() {
        return repository.findAll().stream()
                .map(RoleResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PaginationResponse<RoleResponse> findAllWithPagination(PaginationRequest request) {
        // Build filters dynamically
        Specification<Role> spec = (root, query, cb) -> cb.conjunction();

        if (request.getSearch() != null && !request.getSearch().isEmpty()) {
            String keyword = "%" + request.getSearch().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), keyword),
                    cb.like(cb.lower(root.get("displayName")), keyword)
            ));
        }

        if (request.getFilters() != null && request.getFilters().containsKey("status")) {
            boolean isActive = Boolean.parseBoolean(request.getFilters().get("status"));
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), isActive));
        }

        // Fetch and return
        Page<Role> page = repository.findAll(spec, request.toPageable());
        return PaginationResponse.<RoleResponse>builder()
                .data(page.getContent().stream().map(RoleResponse::fromEntity).collect(Collectors.toList()))
                .meta(PaginationMeta.fromPage(page))
                .build();
    }

    @Override
    public RoleResponse findById(Integer id) {
        return repository.findById(id)
                .map(RoleResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
    }

    @Override
    public RoleResponse create(RoleRequest.CreateRoleRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role with name " + request.getName() + " already exists");
        }

        Role role = Role.builder()
                .name(request.getName())
                .displayName(request.getDisplayName())
                .status(request.getStatus())
                .build();
        
        Role savedRole = repository.save(role);
        
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            assignPermissions(savedRole.getId(), request.getPermissionIds());
            return findById(savedRole.getId());
        }
        
        return RoleResponse.fromEntity(savedRole);
    }

    @Override
    public RoleResponse update(Integer id, RoleRequest.UpdateRoleRequest request) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
        
        // Check if new name is already taken by another role
        repository.findByName(request.getName()).ifPresent(existingRole -> {
            if (!existingRole.getId().equals(id)) {
                throw new DuplicateResourceException("Role with name " + request.getName() + " already exists");
            }
        });

        role.setName(request.getName());
        role.setDisplayName(request.getDisplayName());
        role.setStatus(request.getStatus());
        
        Role updatedRole = repository.save(role);
        
        if (request.getPermissionIds() != null) {
            assignPermissions(updatedRole.getId(), request.getPermissionIds());
            return findById(updatedRole.getId());
        }
        
        return RoleResponse.fromEntity(updatedRole);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with ID: " + id);
        }

        if (userRepository.existsByRole_Id(id)) {
            throw new ResourceInUseException("Cannot delete role because it is currently assigned to users");
        }

        repository.deleteById(id);
    }

    @Override
    public RoleResponse assignPermissions(Integer roleId, List<Integer> permissionIds) {
        Role role = repository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
        
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        
        // Clear existing permissions
        role.getRolePermissions().clear();
        
        // Add new permissions
        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> RolePermission.builder()
                        .role(role)
                        .permission(permission)
                        .build())
                .collect(Collectors.toList());
        
        role.getRolePermissions().addAll(rolePermissions);
        
        return RoleResponse.fromEntity(repository.save(role));
    }

}
