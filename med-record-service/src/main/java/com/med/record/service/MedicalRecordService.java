package com.med.record.service;

import com.med.common.entity.MedicalRecord;
import com.med.record.repository.MedicalRecordRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MedicalRecordService {

    private final MedicalRecordRepository recordRepository;

    public MedicalRecordService(MedicalRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @CacheEvict(value = "recordList", allEntries = true)
    public MedicalRecord create(MedicalRecord record) {
        record.setIsFinished(0);
        return recordRepository.save(record);
    }

    @CacheEvict(value = "recordList", allEntries = true)
    @CachePut(value = "record", key = "#id")
    public MedicalRecord update(Long id, MedicalRecord record) {
        MedicalRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("病历不存在: " + id));
        existing.setChiefComplaint(record.getChiefComplaint());
        existing.setPresentIllness(record.getPresentIllness());
        existing.setPastHistory(record.getPastHistory());
        existing.setPhysicalExamination(record.getPhysicalExamination());
        existing.setDiagnosis(record.getDiagnosis());
        existing.setTreatmentPlan(record.getTreatmentPlan());
        existing.setDoctorAdvice(record.getDoctorAdvice());
        return recordRepository.save(existing);
    }

    @CacheEvict(value = {"recordList", "record"}, allEntries = true)
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }

    @Cacheable(value = "record", key = "#id", unless = "#result == null")
    public MedicalRecord getById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "recordList", unless = "#result.isEmpty()")
    public List<MedicalRecord> listAll() {
        return recordRepository.findAll();
    }

    public List<MedicalRecord> getByPatient(Long patientId) {
        return recordRepository.findByPatientId(patientId);
    }

    public List<MedicalRecord> getByDoctor(Long doctorId) {
        return recordRepository.findByDoctorId(doctorId);
    }

    public List<MedicalRecord> getByAppointment(Long appointmentId) {
        return recordRepository.findByAppointmentId(appointmentId);
    }

    public MedicalRecord finish(Long id) {
        MedicalRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("病历不存在: " + id));
        record.setIsFinished(1);
        return recordRepository.save(record);
    }

    public long count() {
        return recordRepository.count();
    }
}
