package ru.effectivemobile.taskmanagementsystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "comments")
public class Comments extends BaseEntity {
    @NotEmpty(message = "текст комментария не должен быть пустым")
    @Size(max = 500, message = "длина комментария не должна превышать 500 символов")
    private String commentText;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task taskId;
}
