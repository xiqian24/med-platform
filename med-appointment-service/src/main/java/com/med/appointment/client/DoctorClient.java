package com.med.appointment.client;

import com.med.common.dto.R;
import com.med.common.entity.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "med-doctor-service", path = "/api/doctors")
public interface DoctorClient {

    @GetMapping("/{id}")
    R<Doctor> getDoctorById(@PathVariable("id") Long id);
}
