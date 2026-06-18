package com.med.record.controller;

import com.med.common.dto.R;
import com.med.common.entity.Prescription;
import com.med.record.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@Tag(name = "处方管理", description = "处方的增删改查及发药接口")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    @Operation(summary = "新增处方")
    public R<Prescription> create(@RequestBody Prescription prescription) {
        return R.ok("处方创建成功", prescriptionService.create(prescription));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改处方")
    public R<Prescription> update(@PathVariable Long id, @RequestBody Prescription prescription) {
        return R.ok("处方更新成功", prescriptionService.update(id, prescription));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除处方")
    public R<String> delete(@PathVariable Long id) {
        prescriptionService.delete(id);
        return R.ok("处方删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询处方详情")
    public R<Prescription> getById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getById(id);
        if (prescription == null) return R.fail("处方不存在");
        return R.ok(prescription);
    }

    @GetMapping
    @Operation(summary = "查询所有处方")
    public R<List<Prescription>> listAll() {
        return R.ok(prescriptionService.listAll());
    }

    @GetMapping("/by-record/{recordId}")
    @Operation(summary = "查询病历关联的处方")
    public R<List<Prescription>> getByRecord(@PathVariable Long recordId) {
        return R.ok(prescriptionService.getByRecord(recordId));
    }

    @GetMapping("/by-patient/{patientId}")
    @Operation(summary = "查询患者的所有处方")
    public R<List<Prescription>> getByPatient(@PathVariable Long patientId) {
        return R.ok(prescriptionService.getByPatient(patientId));
    }

    @PutMapping("/{id}/dispense")
    @Operation(summary = "发药", description = "将处方标记为已发药")
    public R<Prescription> dispense(@PathVariable Long id) {
        return R.ok("发药成功", prescriptionService.dispense(id));
    }

    @GetMapping("/count")
    @Operation(summary = "统计处方数量")
    public R<Long> count() {
        return R.ok(prescriptionService.count());
    }
}
