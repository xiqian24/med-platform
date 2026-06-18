package com.med.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "med_patient")
public class Patient extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "gender", length = 10)
    private String gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "id_card", length = 18, unique = true)
    private String idCard;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "address", length = 200)
    private String address;
    @Column(name = "blood_type", length = 5)
    private String bloodType;
    @Column(name = "allergy_history", length = 500)
    private String allergyHistory;
    @Column(name = "emergency_contact", length = 50)
    private String emergencyContact;
    @Column(name = "emergency_phone", length = 20)
    private String emergencyPhone;

    public Patient() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public String getAllergyHistory() { return allergyHistory; }
    public void setAllergyHistory(String allergyHistory) { this.allergyHistory = allergyHistory; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
}
