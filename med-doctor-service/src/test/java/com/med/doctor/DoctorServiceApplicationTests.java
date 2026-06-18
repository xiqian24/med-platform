package com.med.doctor;

import com.med.common.entity.Doctor;
import com.med.common.entity.Department;
import com.med.doctor.service.DoctorService;
import com.med.doctor.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DoctorServiceApplicationTests {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        // Clean handled by @Transactional in service
    }

    @Test
    void testCreateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setName("华佗");
        doctor.setTitle("主任医师");
        doctor.setSpecialty("中医内科");
        doctor.setGender("男");
        doctor.setAge(50);
        Doctor saved = doctorService.create(doctor);
        assertNotNull(saved.getId());
        assertEquals("华佗", saved.getName());
        assertEquals("主任医师", saved.getTitle());
    }

    @Test
    void testCreateAndGetDepartment() {
        Department dept = new Department();
        dept.setName("内科");
        dept.setDescription("内科疾病诊疗");
        Department saved = departmentService.create(dept);
        assertNotNull(saved.getId());
        assertEquals("内科", departmentService.getById(saved.getId()).getName());
    }

    @Test
    void testFilterByTitle() {
        Doctor d1 = new Doctor(); d1.setName("医生A"); d1.setTitle("主任医师"); doctorService.create(d1);
        Doctor d2 = new Doctor(); d2.setName("医生B"); d2.setTitle("副主任医师"); doctorService.create(d2);
        Doctor d3 = new Doctor(); d3.setName("医生C"); d3.setTitle("主任医师"); doctorService.create(d3);
        List<Doctor> result = doctorService.filterByTitle("主任医师");
        assertEquals(2, result.size());
    }

    @Test
    void testDoctorCount() {
        for (int i = 0; i < 5; i++) {
            Doctor d = new Doctor(); d.setName("医生" + i); doctorService.create(d);
        }
        assertEquals(5, doctorService.count());
    }
}
