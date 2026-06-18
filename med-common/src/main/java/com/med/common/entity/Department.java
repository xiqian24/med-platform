package com.med.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "med_department")
public class Department extends BaseEntity {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "location", length = 100)
    private String location;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "status")
    private Integer status = 1;

    public Department() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
