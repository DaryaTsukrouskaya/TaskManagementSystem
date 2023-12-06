package ru.effectivemobile.taskmanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "tasks")
public class User extends BaseEntity {
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректное имя")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректная фамилия")
    @Column(name = "surname")
    private String surname;
    @NotNull(message = "заполните поле дата рождения")
    @Past(message = "указанная дата рождения еще не наступила")
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Email(message = "некорректный email")
    @NotBlank(message = "email не должен быть пустым")
    @Column(name = "email")
    private String email;
    @Size(min = 6, max = 10, message = "длина пароля должна быть от 6 до 10 символов")
    @Pattern(regexp = "\\S+",
            message = "пароль не должен содержать пробелы")
    @Column(name = "password")
    private String password;

}
