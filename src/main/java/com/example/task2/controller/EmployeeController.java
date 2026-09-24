package com.example.task2.controller;

import com.example.task2.model.Employee;
import com.example.task2.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String getAllEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            Model model) {

        List<Employee> employees = employeeService.search(name, active);
        model.addAttribute("employees", employees);
        model.addAttribute("currentName", name);
        model.addAttribute("currentActive", active);

        return "employee-list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }


    @PostMapping("/add")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "employee-form";
        }

        if (employee.getId() == null && employeeService.emailExists(employee.getEmail())) {
            bindingResult.rejectValue("email", "error.employee", "Сотрудник с таким email уже существует");
            return "employee-form";
        }

        employeeService.save(employee);
        return "redirect:/employees";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Employee employee = employeeService.getById(id);
            model.addAttribute("employee", employee);
            return "employee-form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/employees";
        }
    }


    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        try {
            employeeService.delete(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/employees";
    }
}
