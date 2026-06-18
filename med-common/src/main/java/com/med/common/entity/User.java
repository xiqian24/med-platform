package com.med.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "med_user")
public class User extends BaseEntity {
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Column(name = "real_name", length = 50)
    private String realName;
    @Column(name = "role", length = 30)
    private String role;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "status")
    private Integer status = 1;

    public User() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
