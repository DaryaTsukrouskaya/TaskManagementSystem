package ru.effectivemobile.taskmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.effectivemobile.taskmanagementsystem.*")
@EntityScan(basePackages = "ru.effectivemobile.taskmanagementsystem.*")
public class TaskManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementSystemApplication.class, args);
    }

}
