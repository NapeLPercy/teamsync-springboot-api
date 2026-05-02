package com.example.backend.dao;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    private final JdbcClient jdbcClient;

    public RoleRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int saveRole(String roleId, String role, String userId) {
        return jdbcClient.sql("INSERT INTO role(id,user_role, user_id) VALUES(?,?,?)")
                .params(List.of(roleId, role, userId))
                .update();
    }
}