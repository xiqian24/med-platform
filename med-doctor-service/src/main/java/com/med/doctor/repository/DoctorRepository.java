package com.med.doctor.repository;

import com.med.common.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByNameContaining(String name);
    List<Doctor> findByDepartmentId(Long departmentId);
    List<Doctor> findByTitle(String title);
    List<Doctor> findBySpecialtyContaining(String specialty);
    List<Doctor> findByStatus(Integer status);
}
