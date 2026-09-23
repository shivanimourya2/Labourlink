package com.labourlink.service;

import com.labourlink.dto.LoginRequest;
import com.labourlink.dto.SignupRequest;
import com.labourlink.model.*;
import com.labourlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(SignupRequest request) {
        // Check if phone already exists
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword()); // plain text for demo
        user.setCity(request.getCity());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        if (user.getRole() == Role.WORKER && request.getPrimarySkill() != null) {
            user.setPrimarySkill(Skill.valueOf(request.getPrimarySkill().toUpperCase()));
        }

        if (user.getRole() == Role.CONTRACTOR && request.getCompanyName() != null) {
            user.setCompanyName(request.getCompanyName());
        }

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        return userRepository.findByPhoneAndPassword(request.getPhone(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid phone or password"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getWorkers(String city, String skill) {
        if (city != null && skill != null) {
            return userRepository.findByRoleAndCityAndPrimarySkill(
                Role.WORKER, city, Skill.valueOf(skill.toUpperCase()));
        } else if (city != null) {
            return userRepository.findByRoleAndCity(Role.WORKER, city);
        } else if (skill != null) {
            return userRepository.findByRoleAndPrimarySkill(
                Role.WORKER, Skill.valueOf(skill.toUpperCase()));
        }
        return userRepository.findByRole(Role.WORKER);
    }

    public void updateWorkerRating(Long workerId, int newRating) {
        User worker = getUserById(workerId);
        int totalRatings = worker.getTotalRatings() == null ? 0 : worker.getTotalRatings();
        double currentAvg = worker.getAverageRating() == null ? 0.0 : worker.getAverageRating();

        double newAvg = ((currentAvg * totalRatings) + newRating) / (totalRatings + 1);
        worker.setAverageRating(Math.round(newAvg * 10.0) / 10.0);
        worker.setTotalRatings(totalRatings + 1);
        userRepository.save(worker);
    }

    public void updateContractorRating(Long contractorId, int newRating) {
        User contractor = getUserById(contractorId);
        int totalRatings = contractor.getContractorTotalRatings() == null ? 0 : contractor.getContractorTotalRatings();
        double currentAvg = contractor.getContractorRating() == null ? 0.0 : contractor.getContractorRating();

        double newAvg = ((currentAvg * totalRatings) + newRating) / (totalRatings + 1);
        contractor.setContractorRating(Math.round(newAvg * 10.0) / 10.0);
        contractor.setContractorTotalRatings(totalRatings + 1);
        userRepository.save(contractor);
    }
}
