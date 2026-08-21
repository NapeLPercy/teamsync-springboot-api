package com.example.backend.dao;

import com.example.backend.dto.Employee;
import com.example.backend.dto.UserResponse;
import com.example.backend.model.User;

import java.util.*;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // add user
    public int saveUser(User user) {
        return jdbcClient
                .sql("INSERT INTO users(id, full_name,company_id) VALUES(?,?,?)")
                .params(List.of(user.getId(), user.getFullName(), user.getCompanyId()))
                .update();
    }

    public Optional<String> getCompanyId(String userId) {
        return jdbcClient
                .sql("SELECT company_id FROM users WHERE id=:user_id")
                .param("user_id", userId)
                .query(String.class)
                .optional();
    }
    

    public List<Employee> getAllEmployees(String companyId) {
        return jdbcClient.sql("""
                SELECT u.id AS userId,
                       u.full_name AS fullName,
                       u.is_active AS isActive,
                       a.email,
                       a.status,
                       a.created_at AS createdAt,
                       r.user_role AS role
                FROM users u
                INNER JOIN account a ON u.id = a.user_id
                INNER JOIN role r ON u.id = r.user_id
                WHERE u.company_id = :company_id
                  AND r.user_role = 'EMPLOYEE'
                """)
                .param("company_id", companyId)
                .query(Employee.class)
                .list();
    }

    public List<UserResponse> fetchEmployeesDetails(String companyId) {
        return jdbcClient
                .sql("""
                        SELECT u.id AS userId, u.is_active AS isActive, u.full_name AS fullName
                        FROM users u
                        INNER JOIN role r ON u.id = r.user_id
                        WHERE u.company_id = :company_id
                          AND r.user_role = 'EMPLOYEE'
                        """)
                .param("company_id", companyId)
                .query(UserResponse.class)
                .list();
    }

    public boolean userBelongsToCompany(// add later AND is_active = true
            String userId,
            String companyId) {

        return jdbcClient
                .sql("""
                        SELECT EXISTS (
                            SELECT 1
                            FROM users
                            WHERE id = :user_id
                            AND company_id = :company_id

                        )
                        """)
                .param("user_id", userId)
                .param("company_id", companyId)
                .query(Boolean.class)
                .single();
    }

    /*
     * public List<User> findAll() {
     * return jdbcClient.sql("SELECT * from users")
     * .query(User.class)
     * .list();
     * }
     * 
     * public Optional<User> findById(String id) {
     * return jdbcClient.sql("SELECT * FROM users WHERE id = :id")
     * .param("id", id)
     * .query(User.class)
     * .optional();
     * }
     * 
     * // get users by email/activeness
     * public List<User> searchUsers(String email, Boolean isActive) {
     * return jdbcClient.sql("""
     * SELECT id, email, password, full_name, role, is_active
     * FROM users
     * WHERE (CAST(:email AS text) IS NULL OR email ILIKE :email)
     * AND (CAST(:isActive AS boolean) IS NULL OR is_active = :isActive)
     * """)
     * .param("email", (email == null || email.isBlank()) ? null : "%" +
     * email.trim() + "%")
     * .param("isActive", isActive)
     * .query(User.class)
     * .list();
     * }
     * 
     * // filtering by mail, activity value
     * public List<User> search(String email, Boolean isActive) {
     * StringBuilder sql = new
     * StringBuilder("SELECT id,email,password,full_name,role,is_active FROM users WHERE 1=1"
     * );
     * Map<String, Object> params = new HashMap<>();
     * 
     * if (email != null && !email.isBlank()) {
     * sql.append(" AND email ILIKE :email"); // use LIKE if you want partial search
     * params.put("email", "%" + email.trim() + "%");
     * }
     * 
     * if (isActive != null) {
     * sql.append(" AND is_active = :isActive");
     * params.put("isActive", isActive);
     * }
     * 
     * return jdbcClient.sql(sql.toString())
     * .params(params)
     * .query(User.class) // or .query(rowMapper)
     * .list();
     * }
     * 
     * // Update
     * public String update(String id, User user) {
     * int updated = jdbcClient
     * .sql("UPDATE users SET email=?, password=?, full_name=?, role=?::user_role,is_active=? WHERE id=?"
     * )
     * .params(List.of(user.getEmail(), user.getPassword(), user.getFullName(),
     * user.getRole().name(),
     * user.isActive(), id))
     * .update();
     * return updated == 1 ? "Sucess" : "failure";
     * }
     * 
     * // Delete
     * public String delete(String id) {
     * int deleted = jdbcClient.sql("DELETE FROM users WHERE id =:idx")
     * .param("idx", id)
     * .update();
     * 
     * return deleted == 1 ? "Successfully deleted" : "Not deleted";
     * }
     * 
     * // users change password
     * public String changePassword(UserChangePassword ucp) {
     * int updated = jdbcClient.sql("UPDATE users SET password=? WHERE id=?")
     * .params(List.of(ucp.getNewPassword(), ucp.getUserId()))
     * .update();
     * return updated == 1 ? "Password changed" : "Password not changed";
     * }
     * 
     */
}