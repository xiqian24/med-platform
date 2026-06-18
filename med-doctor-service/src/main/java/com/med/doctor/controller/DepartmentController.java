package com.med.doctor.controller;

import com.med.common.dto.R;
import com.med.common.entity.Department;
import com.med.doctor.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "科室管理", description = "科室信息的增删改查接口")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @Operation(summary = "新增科室")
    public R<Department> create(@RequestBody Department department) {
        return R.ok("科室添加成功", departmentService.create(department));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改科室")
    public R<Department> update(@PathVariable Long id, @RequestBody Department department) {
        return R.ok("科室更新成功", departmentService.update(id, department));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除科室")
    public R<String> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return R.ok("科室删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询科室详情")
    public R<Department> getById(@PathVariable Long id) {
        Department dept = departmentService.getById(id);
        if (dept == null) return R.fail("科室不存在");
        return R.ok(dept);
    }

    @GetMapping
    @Operation(summary = "查询所有科室")
    public R<List<Department>> listAll() {
        return R.ok(departmentService.listAll());
    }

    @GetMapping("/search")
    @Operation(summary = "按名称搜索科室")
    public R<List<Department>> searchByName(@RequestParam String name) {
        return R.ok(departmentService.searchByName(name));
    }

    @GetMapping("/count")
    @Operation(summary = "统计科室数量")
    public R<Long> count() {
        return R.ok(departmentService.count());
    }
}
