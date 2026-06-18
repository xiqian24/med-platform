package com.med.auth.service;

import com.med.auth.repository.UserRepository;
import com.med.common.dto.LoginRequest;
import com.med.common.dto.LoginResponse;
import com.med.common.dto.R;
import com.med.common.entity.User;
import com.med.common.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public R<LoginResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = JwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUsername(user.getUsername());
        resp.setRealName(user.getRealName());
        resp.setRole(user.getRole());
        return R.ok("登录成功", resp);
    }

    public R<LoginResponse> register(LoginRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return R.fail("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setRealName(request.getUsername());
        user.setStatus(1);
        userRepository.save(user);
        String token = JwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUsername(user.getUsername());
        resp.setRealName(user.getRealName());
        resp.setRole(user.getRole());
        return R.ok("注册成功", resp);
    }
}
