package com.med.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "med_appointment")
public class Appointment extends BaseEntity {
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
    @Column(name = "department_id")
    private Long departmentId;
    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;
    @Column(name = "status")
    private Integer status = 0;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "patient_name", length = 50)
    private String patientName;
    @Column(name = "doctor_name", length = 50)
    private String doctorName;
    @Column(name = "department_name", length = 100)
    private String departmentName;

    public Appointment() {}

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
