package com.example.backend.dao;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import com.example.backend.model.*;

@Repository
public class AccountRepository {

    private final JdbcClient jdbcClient;

    public AccountRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int saveAccount(Account account) {
        return jdbcClient.sql("INSERT INTO account(id, email,password, status, user_id)"
                + "VALUES(?,?,?,?,?)")
                .params(List.of(
                        account.getId(),
                        account.getEmail(),
                        account.getPassword(),
                        account.getStatus(),
                        account.getUserId()))
                .update();
    }
}