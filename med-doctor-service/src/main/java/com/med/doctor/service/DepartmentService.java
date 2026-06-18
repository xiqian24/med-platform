package com.med.doctor.service;

import com.med.common.entity.Department;
import com.med.doctor.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(Department dept) {
        return departmentRepository.save(dept);
    }

    public Department update(Long id, Department dept) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("科室不存在: " + id));
        existing.setName(dept.getName());
        existing.setDescription(dept.getDescription());
        existing.setLocation(dept.getLocation());
        existing.setPhone(dept.getPhone());
        existing.setStatus(dept.getStatus());
        return departmentRepository.save(existing);
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public List<Department> listAll() {
        return departmentRepository.findAll();
    }

    public List<Department> searchByName(String name) {
        return departmentRepository.findByNameContaining(name);
    }

    public long count() {
        return departmentRepository.count();
    }
}
