package com.med.patient.repository;

import com.med.common.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByIdCard(String idCard);
    List<Patient> findByNameContaining(String name);
    List<Patient> findByPhone(String phone);
    List<Patient> findByGender(String gender);
    List<Patient> findByAgeBetween(Integer minAge, Integer maxAge);
}
