package com.med.doctor.repository;

import com.med.common.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByNameContaining(String name);
    List<Department> findByStatus(Integer status);
}
