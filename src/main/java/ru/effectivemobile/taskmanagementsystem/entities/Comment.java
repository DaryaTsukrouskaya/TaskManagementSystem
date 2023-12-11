package ru.effectivemobile.taskmanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @NotEmpty(message = "текст комментария не должен быть пустым")
    @Size(max = 500, message = "длина комментария не должна превышать 500 символов")
    @Column(name = "comment_text")
    private String commentText;
    @Past(message = "указанная дата еще не наступила")
    @Column(name = "date")
    private LocalDate creationDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;


}
