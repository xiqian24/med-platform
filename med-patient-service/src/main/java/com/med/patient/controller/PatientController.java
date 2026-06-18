package com.med.patient.controller;

import com.med.common.dto.R;
import com.med.common.entity.Patient;
import com.med.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "患者管理", description = "患者信息的增删改查及高级搜索接口")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "新增患者", description = "添加一条新的患者记录")
    public R<Patient> create(@RequestBody Patient patient) {
        return R.ok("患者添加成功", patientService.create(patient));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改患者", description = "根据ID更新患者信息")
    public R<Patient> update(@PathVariable Long id, @RequestBody Patient patient) {
        return R.ok("患者信息更新成功", patientService.update(id, patient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除患者", description = "根据ID删除患者记录")
    public R<String> delete(@PathVariable Long id) {
        patientService.delete(id);
        return R.ok("患者删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询患者详情", description = "根据ID查询患者详细信息（使用Redis缓存）")
    public R<Patient> getById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        if (patient == null) {
            return R.fail("患者不存在");
        }
        return R.ok(patient);
    }

    @GetMapping
    @Operation(summary = "查询所有患者", description = "分页查询所有患者列表（使用Redis缓存）")
    public R<List<Patient>> listAll() {
        return R.ok(patientService.listAll());
    }

    @GetMapping("/search")
    @Operation(summary = "按姓名搜索", description = "根据患者姓名模糊搜索")
    public R<List<Patient>> searchByName(@RequestParam String name) {
        return R.ok(patientService.searchByName(name));
    }

    @GetMapping("/by-idcard/{idCard}")
    @Operation(summary = "按身份证查询", description = "根据身份证号精确查询患者")
    public R<Patient> getByIdCard(@PathVariable String idCard) {
        Patient patient = patientService.getByIdCard(idCard);
        if (patient == null) {
            return R.fail("患者不存在");
        }
        return R.ok(patient);
    }

    @GetMapping("/filter/gender")
    @Operation(summary = "按性别筛选", description = "根据性别筛选患者列表")
    public R<List<Patient>> filterByGender(@RequestParam String gender) {
        return R.ok(patientService.filterByGender(gender));
    }

    @GetMapping("/filter/age")
    @Operation(summary = "按年龄范围筛选", description = "根据年龄范围筛选患者列表")
    public R<List<Patient>> filterByAgeRange(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return R.ok(patientService.filterByAgeRange(minAge, maxAge));
    }

    @GetMapping("/count")
    @Operation(summary = "统计患者数量", description = "获取系统中患者总数")
    public R<Long> count() {
        return R.ok("查询成功", patientService.count());
    }

    @PostMapping("/batch")
    @Operation(summary = "批量新增患者", description = "批量添加多个患者记录")
    public R<List<Patient>> batchCreate(@RequestBody List<Patient> patients) {
        List<Patient> saved = patients.stream().map(patientService::create).toList();
        return R.ok("批量添加成功，共" + saved.size() + "条", saved);
    }
}
