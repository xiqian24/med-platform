package com.med.patient;

import com.med.common.entity.Patient;
import com.med.patient.repository.PatientRepository;
import com.med.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PatientServiceApplicationTests {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(patientService);
    }

    @Test
    void testCreatePatient() {
        Patient patient = new Patient();
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(30);
        patient.setPhone("13800138000");
        patient.setIdCard("110101199001011234");

        Patient saved = patientService.create(patient);
        assertNotNull(saved.getId());
        assertEquals("张三", saved.getName());
        assertEquals("男", saved.getGender());
        assertEquals(30, saved.getAge());
    }

    @Test
    void testGetPatientById() {
        Patient patient = new Patient();
        patient.setName("李四");
        patient.setGender("女");
        patient.setAge(25);
        Patient saved = patientService.create(patient);

        Patient found = patientService.getById(saved.getId());
        assertNotNull(found);
        assertEquals("李四", found.getName());
    }

    @Test
    void testUpdatePatient() {
        Patient patient = new Patient();
        patient.setName("王五");
        patient.setAge(40);
        Patient saved = patientService.create(patient);

        saved.setName("王五改");
        saved.setAge(41);
        Patient updated = patientService.update(saved.getId(), saved);

        assertEquals("王五改", updated.getName());
        assertEquals(41, updated.getAge());
    }

    @Test
    void testDeletePatient() {
        Patient patient = new Patient();
        patient.setName("赵六");
        Patient saved = patientService.create(patient);
        assertNotNull(saved.getId());

        patientService.delete(saved.getId());
        Patient found = patientService.getById(saved.getId());
        assertNull(found);
    }

    @Test
    void testListAllPatients() {
        for (int i = 0; i < 5; i++) {
            Patient p = new Patient();
            p.setName("患者" + i);
            patientService.create(p);
        }
        List<Patient> all = patientService.listAll();
        assertEquals(5, all.size());
    }

    @Test
    void testSearchByName() {
        Patient p1 = new Patient();
        p1.setName("张三丰");
        patientService.create(p1);
        Patient p2 = new Patient();
        p2.setName("张三丰之子");
        patientService.create(p2);
        Patient p3 = new Patient();
        p3.setName("李四");
        patientService.create(p3);

        List<Patient> results = patientService.searchByName("张三丰");
        assertEquals(2, results.size());
    }

    @Test
    void testFilterByGender() {
        Patient p1 = new Patient();
        p1.setName("男患者1");
        p1.setGender("男");
        patientService.create(p1);
        Patient p2 = new Patient();
        p2.setName("女患者1");
        p2.setGender("女");
        patientService.create(p2);

        List<Patient> males = patientService.filterByGender("男");
        assertEquals(1, males.size());
    }

    @Test
    void testCount() {
        for (int i = 0; i < 10; i++) {
            Patient p = new Patient();
            p.setName("批量患者" + i);
            patientService.create(p);
        }
        assertEquals(10, patientService.count());
    }
}
