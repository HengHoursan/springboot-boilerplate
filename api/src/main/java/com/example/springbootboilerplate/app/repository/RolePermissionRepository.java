package com.example.springbootboilerplate.app.repository;

import com.example.springbootboilerplate.app.models.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    boolean existsByPermission_Id(Integer permissionId);
}
