package com.example.springbootboilerplate.app.repository;

import com.example.springbootboilerplate.app.models.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
}
