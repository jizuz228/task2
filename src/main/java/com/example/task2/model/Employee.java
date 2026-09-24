package com.example.task2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ФИО сотрудника не должно быть пустым")
    @Size(min = 2, max = 100, message = "ФИО должно содержать от 2 до 100 символов")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неверный формат электронного адреса")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Номер телефона не должен быть пустым")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Номер телефона должен соответствовать международному формату (например, +79991234567)"
    )
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Должность не должна быть пустой")
    @Size(max = 50, message = "Название должности не должно превышать 50 символов")
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull(message = "Заработная плата должна быть указана")
    @DecimalMin(value = "0.0", inclusive = false, message = "Заработная плата должна быть больше 0")
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @NotNull(message = "Статус активности должен быть указан")
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
