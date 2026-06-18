package com.med.doctor.service;

import com.med.common.entity.Doctor;
import com.med.doctor.repository.DoctorRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @CacheEvict(value = "doctorList", allEntries = true)
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @CacheEvict(value = "doctorList", allEntries = true)
    @CachePut(value = "doctor", key = "#id")
    public Doctor update(Long id, Doctor doctor) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("医生不存在: " + id));
        existing.setName(doctor.getName());
        existing.setGender(doctor.getGender());
        existing.setAge(doctor.getAge());
        existing.setTitle(doctor.getTitle());
        existing.setSpecialty(doctor.getSpecialty());
        existing.setDepartmentId(doctor.getDepartmentId());
        existing.setPhone(doctor.getPhone());
        existing.setEmail(doctor.getEmail());
        existing.setIntroduction(doctor.getIntroduction());
        existing.setConsultationFee(doctor.getConsultationFee());
        existing.setStatus(doctor.getStatus());
        return doctorRepository.save(existing);
    }

    @CacheEvict(value = {"doctorList", "doctor"}, allEntries = true)
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    @Cacheable(value = "doctor", key = "#id", unless = "#result == null")
    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "doctorList", unless = "#result.isEmpty()")
    public List<Doctor> listAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> searchByName(String name) {
        return doctorRepository.findByNameContaining(name);
    }

    public List<Doctor> getByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId);
    }

    public List<Doctor> filterByTitle(String title) {
        return doctorRepository.findByTitle(title);
    }

    public List<Doctor> filterBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyContaining(specialty);
    }

    public long count() {
        return doctorRepository.count();
    }
}
