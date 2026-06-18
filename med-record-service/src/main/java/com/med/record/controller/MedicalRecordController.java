package com.med.record.controller;

import com.med.common.dto.R;
import com.med.common.entity.MedicalRecord;
import com.med.record.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@Tag(name = "病历管理", description = "病历的增删改查接口")
public class MedicalRecordController {

    private final MedicalRecordService recordService;

    public MedicalRecordController(MedicalRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    @Operation(summary = "新增病历")
    public R<MedicalRecord> create(@RequestBody MedicalRecord record) {
        return R.ok("病历创建成功", recordService.create(record));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改病历")
    public R<MedicalRecord> update(@PathVariable Long id, @RequestBody MedicalRecord record) {
        return R.ok("病历更新成功", recordService.update(id, record));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除病历")
    public R<String> delete(@PathVariable Long id) {
        recordService.delete(id);
        return R.ok("病历删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询病历详情（Redis缓存）")
    public R<MedicalRecord> getById(@PathVariable Long id) {
        MedicalRecord record = recordService.getById(id);
        if (record == null) return R.fail("病历不存在");
        return R.ok(record);
    }

    @GetMapping
    @Operation(summary = "查询所有病历（Redis缓存）")
    public R<List<MedicalRecord>> listAll() {
        return R.ok(recordService.listAll());
    }

    @GetMapping("/by-patient/{patientId}")
    @Operation(summary = "查询患者的所有病历")
    public R<List<MedicalRecord>> getByPatient(@PathVariable Long patientId) {
        return R.ok(recordService.getByPatient(patientId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    @Operation(summary = "查询医生的所有病历")
    public R<List<MedicalRecord>> getByDoctor(@PathVariable Long doctorId) {
        return R.ok(recordService.getByDoctor(doctorId));
    }

    @GetMapping("/by-appointment/{appointmentId}")
    @Operation(summary = "查询预约关联的病历")
    public R<MedicalRecord> getByAppointment(@PathVariable Long appointmentId) {
        List<MedicalRecord> records = recordService.getByAppointment(appointmentId);
        if (records.isEmpty()) return R.fail("未找到关联病历");
        return R.ok(records.get(0));
    }

    @PutMapping("/{id}/finish")
    @Operation(summary = "完成病历", description = "将病历标记为已完成")
    public R<MedicalRecord> finish(@PathVariable Long id) {
        return R.ok("病历已完成", recordService.finish(id));
    }

    @GetMapping("/count")
    @Operation(summary = "统计病历数量")
    public R<Long> count() {
        return R.ok(recordService.count());
    }
}
