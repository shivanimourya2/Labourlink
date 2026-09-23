package com.labourlink.repository;

import com.labourlink.model.User;
import com.labourlink.model.Role;
import com.labourlink.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByPhoneAndPassword(String phone, String password);
    List<User> findByRole(Role role);
    List<User> findByRoleAndCity(Role role, String city);
    List<User> findByRoleAndPrimarySkill(Role role, Skill primarySkill);
    List<User> findByRoleAndCityAndPrimarySkill(Role role, String city, Skill primarySkill);
    boolean existsByPhone(String phone);
}
