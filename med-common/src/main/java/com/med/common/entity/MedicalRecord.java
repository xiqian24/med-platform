package com.med.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "med_medical_record")
public class MedicalRecord extends BaseEntity {
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
    @Column(name = "appointment_id")
    private Long appointmentId;
    @Column(name = "visit_time")
    private LocalDateTime visitTime;
    @Column(name = "chief_complaint", length = 500)
    private String chiefComplaint;
    @Column(name = "present_illness", length = 2000)
    private String presentIllness;
    @Column(name = "past_history", length = 1000)
    private String pastHistory;
    @Column(name = "physical_examination", length = 1000)
    private String physicalExamination;
    @Column(name = "diagnosis", length = 500)
    private String diagnosis;
    @Column(name = "treatment_plan", length = 2000)
    private String treatmentPlan;
    @Column(name = "doctor_advice", length = 1000)
    private String doctorAdvice;
    @Column(name = "is_finished")
    private Integer isFinished = 0;

    public MedicalRecord() {}

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public LocalDateTime getVisitTime() { return visitTime; }
    public void setVisitTime(LocalDateTime visitTime) { this.visitTime = visitTime; }
    public String getChiefComplaint() { return chiefComplaint; }
    public void setChiefComplaint(String chiefComplaint) { this.chiefComplaint = chiefComplaint; }
    public String getPresentIllness() { return presentIllness; }
    public void setPresentIllness(String presentIllness) { this.presentIllness = presentIllness; }
    public String getPastHistory() { return pastHistory; }
    public void setPastHistory(String pastHistory) { this.pastHistory = pastHistory; }
    public String getPhysicalExamination() { return physicalExamination; }
    public void setPhysicalExamination(String physicalExamination) { this.physicalExamination = physicalExamination; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }
    public String getDoctorAdvice() { return doctorAdvice; }
    public void setDoctorAdvice(String doctorAdvice) { this.doctorAdvice = doctorAdvice; }
    public Integer getIsFinished() { return isFinished; }
    public void setIsFinished(Integer isFinished) { this.isFinished = isFinished; }
}
