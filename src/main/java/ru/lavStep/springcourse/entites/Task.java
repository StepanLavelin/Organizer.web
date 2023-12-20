package ru.lavStep.springcourse.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;



@Getter
@Setter
@Table(name="tasks", schema = "project")
public class Task {

    private Long id;
    private Long performer;
    private String name;
    private String description;
    private LocalDate dateCreated;
    private String deadline;
    private Status status;

    public Task(Long performer, String name,String description, String deadline) {
        this.performer = performer;
        this.name = name;
        this.description = description;
        this.dateCreated = LocalDate.now();
        this.deadline = deadline;
        this.status = Status.InWork;
    }

    public Task(){}
}
