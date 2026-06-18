package com.med.patient.service;

import com.med.common.entity.Patient;
import com.med.patient.repository.PatientRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @CacheEvict(value = "patientList", allEntries = true)
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @CacheEvict(value = "patientList", allEntries = true)
    @CachePut(value = "patient", key = "#id")
    public Patient update(Long id, Patient patient) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("患者不存在: " + id));
        existing.setName(patient.getName());
        existing.setGender(patient.getGender());
        existing.setAge(patient.getAge());
        existing.setIdCard(patient.getIdCard());
        existing.setPhone(patient.getPhone());
        existing.setAddress(patient.getAddress());
        existing.setBloodType(patient.getBloodType());
        existing.setAllergyHistory(patient.getAllergyHistory());
        existing.setEmergencyContact(patient.getEmergencyContact());
        existing.setEmergencyPhone(patient.getEmergencyPhone());
        return patientRepository.save(existing);
    }

    @CacheEvict(value = {"patientList", "patient"}, allEntries = true)
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    @Cacheable(value = "patient", key = "#id", unless = "#result == null")
    public Patient getById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "patientList", unless = "#result.isEmpty()")
    public List<Patient> listAll() {
        return patientRepository.findAll();
    }

    public List<Patient> searchByName(String name) {
        return patientRepository.findByNameContaining(name);
    }

    public Patient getByIdCard(String idCard) {
        return patientRepository.findByIdCard(idCard).orElse(null);
    }

    public List<Patient> filterByGender(String gender) {
        return patientRepository.findByGender(gender);
    }

    public List<Patient> filterByAgeRange(Integer minAge, Integer maxAge) {
        return patientRepository.findByAgeBetween(minAge, maxAge);
    }

    public long count() {
        return patientRepository.count();
    }
}
