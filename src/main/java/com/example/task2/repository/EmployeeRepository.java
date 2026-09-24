package com.example.task2.repository;

import com.example.task2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    List<Employee> findByActive(Boolean active);

    List<Employee> findByFullNameContainingIgnoreCaseAndActive(String fullName, Boolean active);
}
