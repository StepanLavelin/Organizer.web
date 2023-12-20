package ru.lavStep.springcourse.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import ru.lavStep.springcourse.entites.Role;

@Getter
@Setter
@Table(name="users", schema = "project")
public class User {

    private Long id;
    private String firstName;

    private String lastName;

    private Role role;


    private Task[] tasks;

    public User(Long id, String firstName, String lastName, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.superUser;
    }
    public User(){}
}
