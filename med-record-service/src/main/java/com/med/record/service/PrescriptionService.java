package com.med.record.service;

import com.med.common.entity.Prescription;
import com.med.record.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public Prescription create(Prescription prescription) {
        prescription.setIsDispensed(0);
        return prescriptionRepository.save(prescription);
    }

    public Prescription update(Long id, Prescription prescription) {
        Prescription existing = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("处方不存在: " + id));
        existing.setMedicationName(prescription.getMedicationName());
        existing.setSpecification(prescription.getSpecification());
        existing.setDosage(prescription.getDosage());
        existing.setUsageMethod(prescription.getUsageMethod());
        existing.setFrequency(prescription.getFrequency());
        existing.setDuration(prescription.getDuration());
        existing.setQuantity(prescription.getQuantity());
        existing.setRemark(prescription.getRemark());
        return prescriptionRepository.save(existing);
    }

    public void delete(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public Prescription getById(Long id) {
        return prescriptionRepository.findById(id).orElse(null);
    }

    public List<Prescription> listAll() {
        return prescriptionRepository.findAll();
    }

    public List<Prescription> getByRecord(Long recordId) {
        return prescriptionRepository.findByRecordId(recordId);
    }

    public List<Prescription> getByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public Prescription dispense(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("处方不存在: " + id));
        prescription.setIsDispensed(1);
        return prescriptionRepository.save(prescription);
    }

    public long count() {
        return prescriptionRepository.count();
    }
}
