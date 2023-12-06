package ru.effectivemobile.taskmanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Column(name = "title")
    @NotBlank(message = "заголовок задачи не должен быть пустым")
    @Size(max = 60, message = "длина заголовка не должна превышать 60 символов")
    private String title;
    @Column(name = "description")
    @NotBlank(message = "описание задачи не должно быть пустым")
    @Size(max = 2000, message = "длина описания задачи не должна превышать 2000 символов")
    private String description;
    @Column(name = "status")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z]+$", message = "некорректный статус")
    @NotBlank(message = "статус задачи не должен быть пустым")
    private String status;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tasks_users",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> performers;
}
