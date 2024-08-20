package com.practicespring.stay_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practicespring.stay_manager.entities.Admin;


@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Admin findAdminByAdminEmail(String email);
}
