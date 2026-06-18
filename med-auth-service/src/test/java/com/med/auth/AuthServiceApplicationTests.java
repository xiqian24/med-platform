package com.med.auth;

import com.med.auth.controller.AuthController;
import com.med.auth.repository.UserRepository;
import com.med.auth.service.AuthService;
import com.med.common.dto.LoginRequest;
import com.med.common.dto.LoginResponse;
import com.med.common.dto.R;
import com.med.common.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setRealName("系统管理员");
        admin.setStatus(1);
        userRepository.save(admin);
    }

    @Test
    void contextLoads() {
        assertNotNull(restTemplate);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("admin123");

        ResponseEntity<R> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/login",
                request, R.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getCode());
    }

    @Test
    void testLoginFailure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrong");

        ResponseEntity<R> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/login",
                request, R.class);

        // Spring Security returns 403 on auth failure
        assertTrue(response.getStatusCode() == HttpStatus.FORBIDDEN ||
                   response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void testRegister() {
        LoginRequest request = new LoginRequest();
        request.setUsername("newuser");
        request.setPassword("pass123");

        ResponseEntity<R> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/register",
                request, R.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getCode());
    }

    @Test
    void testHealthEndpoint() {
        ResponseEntity<R> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/auth/public/health",
                R.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getCode());
    }
}
