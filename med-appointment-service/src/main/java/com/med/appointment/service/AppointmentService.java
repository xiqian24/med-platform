package com.med.appointment.service;

import com.med.appointment.client.DoctorClient;
import com.med.appointment.client.PatientClient;
import com.med.appointment.repository.AppointmentRepository;
import com.med.common.dto.R;
import com.med.common.entity.Appointment;
import com.med.common.entity.Doctor;
import com.med.common.entity.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientClient patientClient, DoctorClient doctorClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    public Appointment create(Appointment appointment) {
        // 远程调用患者服务获取患者信息
        R<Patient> patientR = patientClient.getPatientById(appointment.getPatientId());
        if (patientR != null && patientR.getCode() == 200 && patientR.getData() != null) {
            appointment.setPatientName(patientR.getData().getName());
        } else {
            throw new RuntimeException("患者不存在，无法创建预约");
        }

        // 远程调用医生服务获取医生信息
        R<Doctor> doctorR = doctorClient.getDoctorById(appointment.getDoctorId());
        if (doctorR != null && doctorR.getCode() == 200 && doctorR.getData() != null) {
            Doctor doctor = doctorR.getData();
            appointment.setDoctorName(doctor.getName());
            appointment.setDepartmentId(doctor.getDepartmentId());
            appointment.setDepartmentName("科室" + doctor.getDepartmentId());
        } else {
            throw new RuntimeException("医生不存在，无法创建预约");
        }

        appointment.setStatus(0); // 0-待确认
        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment appointment) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预约不存在: " + id));
        existing.setAppointmentTime(appointment.getAppointmentTime());
        existing.setStatus(appointment.getStatus());
        existing.setDescription(appointment.getDescription());
        return appointmentRepository.save(existing);
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public List<Appointment> listAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> filterByStatus(Integer status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment confirm(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预约不存在: " + id));
        appointment.setStatus(1); // 1-已确认
        return appointmentRepository.save(appointment);
    }

    public Appointment cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预约不存在: " + id));
        appointment.setStatus(2); // 2-已取消
        return appointmentRepository.save(appointment);
    }

    public Appointment complete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预约不存在: " + id));
        appointment.setStatus(3); // 3-已完成
        return appointmentRepository.save(appointment);
    }

    public long count() {
        return appointmentRepository.count();
    }
}
