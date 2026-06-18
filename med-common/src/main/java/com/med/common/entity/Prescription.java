package com.med.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "med_prescription")
public class Prescription extends BaseEntity {
    @Column(name = "record_id", nullable = false)
    private Long recordId;
    @Column(name = "patient_id")
    private Long patientId;
    @Column(name = "doctor_id")
    private Long doctorId;
    @Column(name = "medication_name", length = 200)
    private String medicationName;
    @Column(name = "specification", length = 100)
    private String specification;
    @Column(name = "dosage", length = 100)
    private String dosage;
    @Column(name = "usage_method", length = 200)
    private String usageMethod;
    @Column(name = "frequency", length = 100)
    private String frequency;
    @Column(name = "duration", length = 100)
    private String duration;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "remark", length = 500)
    private String remark;
    @Column(name = "is_dispensed")
    private Integer isDispensed = 0;

    public Prescription() {}

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getUsageMethod() { return usageMethod; }
    public void setUsageMethod(String usageMethod) { this.usageMethod = usageMethod; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public Integer getIsDispensed() { return isDispensed; }
    public void setIsDispensed(Integer isDispensed) { this.isDispensed = isDispensed; }
}
