package com.med.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "med_doctor")
public class Doctor extends BaseEntity {
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "gender", length = 10)
    private String gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "title", length = 50)
    private String title;
    @Column(name = "specialty", length = 100)
    private String specialty;
    @Column(name = "department_id")
    private Long departmentId;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "introduction", length = 500)
    private String introduction;
    @Column(name = "consultation_fee")
    private Double consultationFee;
    @Column(name = "status")
    private Integer status = 1;

    public Doctor() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
