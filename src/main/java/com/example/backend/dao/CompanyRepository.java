package com.example.backend.dao;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import com.example.backend.model.*;

@Repository
public class CompanyRepository {
    private JdbcClient jdbcClient;

    public CompanyRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int saveCompany(Company company) {
        return jdbcClient.sql("INSERT INTO company (id, name) VALUES(?,?)")
                .params(List.of(company.getId(), company.getName()))
                .update();
    }
}
