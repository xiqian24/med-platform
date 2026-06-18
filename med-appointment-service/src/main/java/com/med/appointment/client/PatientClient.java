package com.med.appointment.client;

import com.med.common.dto.R;
import com.med.common.entity.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "med-patient-service", path = "/api/patients")
public interface PatientClient {

    @GetMapping("/{id}")
    R<Patient> getPatientById(@PathVariable("id") Long id);
}
