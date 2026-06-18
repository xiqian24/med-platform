package com.med.doctor.controller;

import com.med.common.dto.R;
import com.med.common.entity.Doctor;
import com.med.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "医生管理", description = "医生信息的增删改查及搜索接口")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "新增医生")
    public R<Doctor> create(@RequestBody Doctor doctor) {
        return R.ok("医生添加成功", doctorService.create(doctor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改医生")
    public R<Doctor> update(@PathVariable Long id, @RequestBody Doctor doctor) {
        return R.ok("医生信息更新成功", doctorService.update(id, doctor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除医生")
    public R<String> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return R.ok("医生删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询医生详情（Redis缓存）")
    public R<Doctor> getById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        if (doctor == null) return R.fail("医生不存在");
        return R.ok(doctor);
    }

    @GetMapping
    @Operation(summary = "查询所有医生（Redis缓存）")
    public R<List<Doctor>> listAll() {
        return R.ok(doctorService.listAll());
    }

    @GetMapping("/search")
    @Operation(summary = "按姓名搜索医生")
    public R<List<Doctor>> searchByName(@RequestParam String name) {
        return R.ok(doctorService.searchByName(name));
    }

    @GetMapping("/by-department/{deptId}")
    @Operation(summary = "按科室查询医生")
    public R<List<Doctor>> getByDepartment(@PathVariable Long deptId) {
        return R.ok(doctorService.getByDepartment(deptId));
    }

    @GetMapping("/filter/title")
    @Operation(summary = "按职称筛选")
    public R<List<Doctor>> filterByTitle(@RequestParam String title) {
        return R.ok(doctorService.filterByTitle(title));
    }

    @GetMapping("/filter/specialty")
    @Operation(summary = "按专业筛选")
    public R<List<Doctor>> filterBySpecialty(@RequestParam String specialty) {
        return R.ok(doctorService.filterBySpecialty(specialty));
    }

    @GetMapping("/count")
    @Operation(summary = "统计医生数量")
    public R<Long> count() {
        return R.ok(doctorService.count());
    }
}
