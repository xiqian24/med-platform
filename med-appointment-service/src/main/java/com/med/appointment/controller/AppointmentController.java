package com.med.appointment.controller;

import com.med.appointment.service.AppointmentService;
import com.med.common.dto.R;
import com.med.common.entity.Appointment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "预约管理", description = "预约信息的增删改查及状态管理，演示跨服务Feign远程调用")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "创建预约", description = "创建预约时会通过Feign远程调用患者服务和医生服务获取信息")
    public R<Appointment> create(@RequestBody Appointment appointment) {
        try {
            return R.ok("预约创建成功", appointmentService.create(appointment));
        } catch (RuntimeException e) {
            return R.fail(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改预约")
    public R<Appointment> update(@PathVariable Long id, @RequestBody Appointment appointment) {
        return R.ok(appointmentService.update(id, appointment));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除预约")
    public R<String> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return R.ok("预约删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询预约详情")
    public R<Appointment> getById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment == null) return R.fail("预约不存在");
        return R.ok(appointment);
    }

    @GetMapping
    @Operation(summary = "查询所有预约")
    public R<List<Appointment>> listAll() {
        return R.ok(appointmentService.listAll());
    }

    @GetMapping("/by-patient/{patientId}")
    @Operation(summary = "查询患者的所有预约")
    public R<List<Appointment>> getByPatient(@PathVariable Long patientId) {
        return R.ok(appointmentService.getByPatient(patientId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    @Operation(summary = "查询医生的所有预约")
    public R<List<Appointment>> getByDoctor(@PathVariable Long doctorId) {
        return R.ok(appointmentService.getByDoctor(doctorId));
    }

    @GetMapping("/filter/status")
    @Operation(summary = "按状态筛选预约")
    public R<List<Appointment>> filterByStatus(@RequestParam Integer status) {
        return R.ok(appointmentService.filterByStatus(status));
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "确认预约", description = "将预约状态修改为已确认(1)")
    public R<Appointment> confirm(@PathVariable Long id) {
        return R.ok("预约已确认", appointmentService.confirm(id));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消预约", description = "将预约状态修改为已取消(2)")
    public R<Appointment> cancel(@PathVariable Long id) {
        return R.ok("预约已取消", appointmentService.cancel(id));
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "完成预约", description = "将预约状态修改为已完成(3)")
    public R<Appointment> complete(@PathVariable Long id) {
        return R.ok("预约已完成", appointmentService.complete(id));
    }

    @GetMapping("/count")
    @Operation(summary = "统计预约数量")
    public R<Long> count() {
        return R.ok(appointmentService.count());
    }
}
