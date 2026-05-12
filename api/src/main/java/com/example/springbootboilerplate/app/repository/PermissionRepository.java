package com.example.springbootboilerplate.app.repository;

import com.example.springbootboilerplate.app.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
}
