package com.example.backend.dao;

import java.util.*;

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

    public Optional<String> findAccountByEmail(String email) {
        return jdbcClient.sql("SELECT email FROM account WHERE email = :email")
                .param("email", email)
                .query(String.class)
                .optional();
    }

    public Optional<Account> loginUser(String email) {
        return jdbcClient.sql("""
                    SELECT
                        a.id,
                        a.email,
                        a.password,
                        ur.user_role
                    FROM account a
                    LEFT JOIN role ur ON ur.user_id = a.user_id
                    WHERE a.email = :email
                    LIMIT 1
                """)
                .param("email", email)
                .query(Account.class)
                .optional();
    }
}