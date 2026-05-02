package com.example.backend.model;

import java.util.*;
import java.time.LocalDateTime;

public class User {
    private String id;
    private String fullName;
    private UserRole role;
    private boolean isActive;

    private String companyId;

    public User(String id, String fullName, String companyId) {
        this.id = id;
        this.fullName = fullName;
        this.companyId = companyId;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

   
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
 


    

}
