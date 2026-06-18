package com.med.auth.controller;

import com.med.auth.service.AuthService;
import com.med.common.dto.LoginRequest;
import com.med.common.dto.LoginResponse;
import com.med.common.dto.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户登录、注册、令牌刷新接口")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "使用用户名和密码进行登录，返回JWT令牌")
    public R<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册新用户账号")
    public R<LoginResponse> register(@RequestBody LoginRequest request) {
        return authService.register(request);
    }

    @GetMapping("/public/health")
    @Operation(summary = "健康检查", description = "检查认证服务是否正常运行")
    public R<String> health() {
        return R.ok("Auth Service is running");
    }
}
