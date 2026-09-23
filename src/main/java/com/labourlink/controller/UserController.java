package com.labourlink.controller;

import com.labourlink.dto.*;
import com.labourlink.model.User;
import com.labourlink.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.signup(request);
            return ResponseEntity.ok(ApiResponse.ok("Registration successful", user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request);
            return ResponseEntity.ok(ApiResponse.ok("Login successful", user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.ok("User found", user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/workers")
    public ResponseEntity<ApiResponse> getWorkers(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String skill) {
        try {
            List<User> workers = userService.getWorkers(city, skill);
            return ResponseEntity.ok(ApiResponse.ok("Workers found", workers));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
