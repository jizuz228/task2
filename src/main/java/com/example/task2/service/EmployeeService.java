package com.example.task2.service;

import com.example.task2.model.Employee;
import com.example.task2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> search(String name, Boolean active) {
        if (name != null && !name.isBlank() && active != null) {
            return employeeRepository.findByFullNameContainingIgnoreCaseAndActive(name, active);
        } else if (name != null && !name.isBlank()) {
            return employeeRepository.findByFullNameContainingIgnoreCase(name);
        } else if (active != null) {
            return employeeRepository.findByActive(active);
        } else {
            return employeeRepository.findAll();
        }
    }


    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник с ID " + id + " не найден"));
    }


    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }


    public Employee save(Employee employee) {
        // Проверка уникальности email при создании нового сотрудника
        if (employee.getId() == null && emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("Сотрудник с таким email уже существует");
        }
        return employeeRepository.save(employee);
    }


    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Невозможно удалить: сотрудник с ID " + id + " не найден");
        }
        employeeRepository.deleteById(id);
    }
}
